package com.akillienerji.AkilliEnerjiSistemi.ml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;

public class MachineLearningPerformanceTest {

    private static final Logger logger = LoggerFactory.getLogger(MachineLearningPerformanceTest.class);
    private AnomalyDetectionService anomalyDetectionService;

    @BeforeEach
    public void setUp() {
        anomalyDetectionService = new AnomalyDetectionService();
        ReflectionTestUtils.setField(anomalyDetectionService, "zScoreThreshold", 2.5);
    }

    @Test
    public void testNormalLoadScenario() {
        logger.info("=== Normal Senaryo Testi (1,000 veri noktası) Başlıyor ===");
        
        // Verileri istatistik havuzuna ekle
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            anomalyDetectionService.addDataPoint(100 + random.nextDouble() * 20); // 100-120 arasi normal veri
        }

        double[] valuesToTest = new double[1000];
        for (int i = 0; i < 1000; i++) {
            valuesToTest[i] = 100 + random.nextDouble() * 20;
        }

        long startTime = System.currentTimeMillis();
        List<Integer> anomalies = anomalyDetectionService.batchDetect(valuesToTest);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        logger.info("Normal Senaryo Tamamlandı. Süre: {} ms, Bulunan Anomali Sayısı: {}", duration, anomalies.size());
        
        assertTrue(duration < 500, "1000 veri analizi 500ms altında tamamlanmalı.");
    }

    @Test
    public void testStressLoadScenario() {
        logger.info("=== Stres/Yük Senaryosu Testi (100,000 veri noktası) Başlıyor ===");
        
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            anomalyDetectionService.addDataPoint(100 + random.nextDouble() * 20);
        }

        double[] valuesToTest = new double[100000];
        for (int i = 0; i < 100000; i++) {
            valuesToTest[i] = 100 + random.nextDouble() * 20;
        }

        long startTime = System.currentTimeMillis();
        List<Integer> anomalies = anomalyDetectionService.batchDetect(valuesToTest);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        logger.info("Stres/Yük Senaryosu Tamamlandı. Süre: {} ms", duration);
        
        // Optimizasyon sonrası 100.000 veri bile çok kısa sürmelidir.
        assertTrue(duration < 2000, "100.000 veri analizi 2000ms altında tamamlanmalı.");
    }

    @Test
    public void testAccuracyScenario() {
        logger.info("=== Kesinlik (Accuracy) Senaryosu Testi Başlıyor ===");
        
        // İstatistik havuzu için temel veriler
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            anomalyDetectionService.addDataPoint(200 + random.nextDouble() * 20 - 10); // 190W ile 210W arasi
        }

        // Test dizisi: Normalde 200W civarında olan bir sisteme aniden 5000W eklenirse
        double[] valuesToTest = {201, 199, 205, 5000, 198, 202, 10, 200}; // 5000 ve 10 anomali
        
        List<Integer> anomalies = anomalyDetectionService.batchDetect(valuesToTest);
        
        logger.info("Bulunan Anomali İndeksleri: {}", anomalies);
        assertEquals(2, anomalies.size(), "Tam olarak 2 anomali tespit edilmeli.");
        assertTrue(anomalies.contains(3), "5000W olan değer (indeks 3) anomali olarak yakalanmalı.");
        assertTrue(anomalies.contains(6), "10W olan değer (indeks 6) anomali olarak yakalanmalı.");
    }
}
