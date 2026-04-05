package com.akillienerji.AkilliEnerjiSistemi.ml;

import com.akillienerji.AkilliEnerjiSistemi.model.SensorReading;
import com.akillienerji.AkilliEnerjiSistemi.repository.InfluxDBRepository;
import com.akillienerji.AkilliEnerjiSistemi.service.AlertService;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Anomali tespit servisi.
 * Istatistiksel Z-Score yontemiyle anormal enerji tuketim
 * paternlerini tespit eder.
 *
 * Algoritmalar:
 *   - Z-Score (mevcut)
 *   - Gelecekte: Isolation Forest (Smile ML)
 *   - Gelecekte: K-Means Clustering
 *
 * Ozellikler:
 *   - Anlik anomali tespiti
 *   - Periyodik tarama (her 5 dakikada bir)
 *   - Otomatik alarm uretimi
 */
@Service
public class AnomalyDetectionService {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectionService.class);

    @Autowired
    private InfluxDBRepository influxDBRepository;

    @Autowired
    private AlertService alertService;

    @Value("${ml.anomaly.zscore-threshold:2.5}")
    private double zScoreThreshold;

    private final DescriptiveStatistics statistics = new DescriptiveStatistics(100); // Son 100 veri noktasi

    /**
     * Her 5 dakikada bir anomali taramasi yapar.
     */
    @Scheduled(fixedRate = 5 * 60 * 1000) // 5 dakika
    public void scanForAnomalies() {
        logger.debug("Anomali taramasi baslatiliyor...");
        try {
            // Son 10 dakikadaki verileri kontrol et
            List<SensorReading> recentData =
                    influxDBRepository.queryRecentData("enerji_tuketim", 10);

            if (recentData.isEmpty()) {
                logger.debug("Taranacak veri bulunamadi. Simule veri kullaniliyor.");
                return;
            }

            // Istatistikleri guncelle
            for (SensorReading reading : recentData) {
                statistics.addValue(reading.getValue());
            }

            // Her veri noktasini kontrol et
            for (SensorReading reading : recentData) {
                AnomalyResult result = detectAnomaly(reading.getValue());
                if (result.isAnomaly) {
                    String msg = String.format(
                            "ANOMALI TESPIT EDILDI: Cihaz %s, Deger: %.1f W, Z-Score: %.2f, Beklenen aralik: %.1f - %.1f W",
                            reading.getDeviceId(), reading.getValue(), result.zScore,
                            result.expectedLower, result.expectedUpper);
                    alertService.createAlert(msg, "WARNING", reading.getDeviceId());
                    logger.warn(msg);
                }
            }
        } catch (Exception e) {
            logger.error("Anomali tarama hatasi: {}", e.getMessage());
        }
    }

    /**
     * Tek bir degeri anomali acisindan kontrol eder.
     *
     * Z-Score hesabi:
     *   z = (x - ortalama) / standart sapma
     *   |z| > esik ise anomali
     *
     * @param value Kontrol edilecek deger
     * @return Anomali sonucu
     */
    public AnomalyResult detectAnomaly(double value) {
        if (statistics.getN() < 10) {
            // Yeterli veri yoksa anomali tespit etme
            return new AnomalyResult(false, 0, 0, 0);
        }

        double mean = statistics.getMean();
        double stdDev = statistics.getStandardDeviation();

        if (stdDev == 0) {
            return new AnomalyResult(false, 0, mean, mean);
        }

        double zScore = (value - mean) / stdDev;
        boolean isAnomaly = Math.abs(zScore) > zScoreThreshold;

        double expectedLower = mean - zScoreThreshold * stdDev;
        double expectedUpper = mean + zScoreThreshold * stdDev;

        return new AnomalyResult(isAnomaly, zScore, expectedLower, expectedUpper);
    }

    /**
     * Istatistik havuzuna yeni veri ekler.
     * MQTT handler veya servislerden cagrilabilir.
     */
    public void addDataPoint(double value) {
        statistics.addValue(value);
    }

    /**
     * Mevcut istatistikleri dondurur.
     */
    public String getStatsSummary() {
        if (statistics.getN() < 2) return "Yeterli veri yok.";
        return String.format(
                "Veri sayisi: %d, Ortalama: %.2f, Std Sapma: %.2f, Min: %.2f, Max: %.2f, Z-Score Esigi: %.1f",
                statistics.getN(), statistics.getMean(), statistics.getStandardDeviation(),
                statistics.getMin(), statistics.getMax(), zScoreThreshold);
    }

    /**
     * Son N veri noktasi icin toplu anomali tespiti yapar.
     *
     * @param values Kontrol edilecek degerler
     * @return Anomali tespit edilen indeksler
     */
    public List<Integer> batchDetect(double[] values) {
        List<Integer> anomalyIndices = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            AnomalyResult result = detectAnomaly(values[i]);
            if (result.isAnomaly) {
                anomalyIndices.add(i);
            }
        }
        return anomalyIndices;
    }

    /**
     * Anomali tespit sonucu.
     */
    public static class AnomalyResult {
        public final boolean isAnomaly;
        public final double zScore;
        public final double expectedLower;
        public final double expectedUpper;

        public AnomalyResult(boolean isAnomaly, double zScore,
                             double expectedLower, double expectedUpper) {
            this.isAnomaly = isAnomaly;
            this.zScore = zScore;
            this.expectedLower = expectedLower;
            this.expectedUpper = expectedUpper;
        }
    }
}
