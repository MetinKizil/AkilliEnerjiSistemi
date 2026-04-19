package com.akillienerji.AkilliEnerjiSistemi.ml;

import com.akillienerji.AkilliEnerjiSistemi.dto.EnergyForecastDTO;
import com.akillienerji.AkilliEnerjiSistemi.model.SensorReading;
import com.akillienerji.AkilliEnerjiSistemi.repository.InfluxDBRepository;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Enerji tuketim tahmin servisi.
 * Apache Commons Math SimpleRegression kullanarak
 * gecmis verilerden gelecek tuketimi tahmin eder.
 *
 * Algoritmalar:
 *   - Linear Regression (SimpleRegression)
 *   - Gelecekte: Random Forest (Smile ML)
 *
 * Periyodik guncelleme: Her 6 saatte bir model yeniden egitilir.
 */
@Service
public class EnergyForecastService {

    private static final Logger logger = LoggerFactory.getLogger(EnergyForecastService.class);

    @Autowired
    private InfluxDBRepository influxDBRepository;

    private SimpleRegression regression;
    private boolean modelTrained = false;
    private LocalDateTime lastTrainTime;

    @PostConstruct
    public void init() {
        regression = new SimpleRegression();
        logger.info("EnergyForecastService baslatildi. Model egitimi bekleniyor...");
    }

    /**
     * Modeli gecmis verilerle egitir.
     * Her 6 saatte bir otomatik olarak calisir.
     */
    @Scheduled(fixedRate = 6 * 60 * 60 * 1000) // 6 saat
    public void trainModel() {
        logger.info("Enerji tahmin modeli egitiliyor...");
        try {
            // Son 24 saatlik veriyi cek
            List<SensorReading> historicalData =
                    influxDBRepository.queryHistoryData("enerji_tuketim", 24);

            if (historicalData.size() < 10) {
                logger.warn("Yeterli veri yok ({}). Model egitimi icin en az 10 veri noktasi gerekli.",
                        historicalData.size());
                // Simule veriyle egit (gelistirme ortami icin)
                trainWithSimulatedData();
                return;
            }

            // Regresyon modelini sifirla ve yeniden egit
            regression.clear();
            for (int i = 0; i < historicalData.size(); i++) {
                regression.addData(i, historicalData.get(i).getValue());
            }

            modelTrained = true;
            lastTrainTime = LocalDateTime.now();
            logger.info("Model basariyla egitildi. R-kare: {:.4f}, Egim: {:.4f}",
                    regression.getRSquare(), regression.getSlope());

        } catch (Exception e) {
            logger.error("Model egitim hatasi: {}", e.getMessage());
            trainWithSimulatedData();
        }
    }

    /**
     * Gelistirme ortami icin simule veriyle egitim yapar.
     */
    private void trainWithSimulatedData() {
        regression.clear();
        // 24 saatlik simule enerji profili
        double[] hourlyPattern = {
                1.2, 1.0, 0.9, 0.8, 1.0, 1.5,  // 00-05: Gece
                3.0, 3.5, 3.2, 2.5, 2.3, 2.5,  // 06-11: Sabah
                3.0, 2.8, 2.3, 2.5, 2.8, 3.5,  // 12-17: Ogleden sonra
                4.2, 4.0, 3.5, 2.8, 2.0, 1.5   // 18-23: Aksam
        };

        for (int i = 0; i < hourlyPattern.length; i++) {
            regression.addData(i, hourlyPattern[i]);
        }

        modelTrained = true;
        lastTrainTime = LocalDateTime.now();
        logger.info("Model simule veriyle egitildi (gelistirme modu).");
    }

    /**
     * Gelecek N saat icin enerji tuketim tahmini yapar.
     *
     * @param hours Tahmin yapilacak saat sayisi
     * @return Tahmin sonuclari listesi
     */
    public List<EnergyForecastDTO> predictNextHours(int hours) {
        if (!modelTrained) {
            trainModel();
        }

        List<EnergyForecastDTO> forecasts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        int currentHourIndex = now.getHour();

        for (int i = 1; i <= hours; i++) {
            int futureIndex = currentHourIndex + i;
            double predicted = regression.predict(futureIndex);
            predicted = Math.max(0, predicted); // Negatif tahmin onleme

            // Guven araligi hesapla (basit standart hata tabanli)
            double stdError = regression.getSlopeStdErr() * Math.sqrt(i);
            double lower = Math.max(0, predicted - 1.96 * stdError);
            double upper = predicted + 1.96 * stdError;

            EnergyForecastDTO forecast = new EnergyForecastDTO(
                    now.plusHours(i),
                    round2(predicted),
                    round2(lower),
                    round2(upper),
                    95.0,
                    "LINEAR_REGRESSION"
            );
            forecasts.add(forecast);
        }

        logger.info("{} saatlik enerji tahmini olusturuldu.", hours);
        return forecasts;
    }

    /**
     * Modelin egitilip egitilmedigini dondurur.
     */
    public boolean isModelTrained() {
        return modelTrained;
    }

    /**
     * Son egitim zamanini dondurur.
     */
    public LocalDateTime getLastTrainTime() {
        return lastTrainTime;
    }

    /**
     * Model istatistiklerini dondurur.
     */
    public String getModelStats() {
        if (!modelTrained) return "Model henuz egitilmedi.";
        return String.format("R²=%.4f, Egim=%.4f, Kesisim=%.4f, Son Egitim=%s",
                regression.getRSquare(), regression.getSlope(),
                regression.getIntercept(), lastTrainTime);
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
