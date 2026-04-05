package com.akillienerji.AkilliEnerjiSistemi.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * InfluxDB baglanti konfigurasyonu.
 * application.properties dosyasindan ayarlari okuyarak
 * InfluxDBClient bean'i olusturur.
 */
@Configuration
public class InfluxDBConfig {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDBConfig.class);

    @Value("${influxdb.url}")
    private String url;

    @Value("${influxdb.token}")
    private String token;

    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

    private InfluxDBClient influxDBClient;

    /**
     * InfluxDB istemci bean'i olusturur.
     * Uygulama ayaga kalktiginda otomatik olarak baglanir.
     */
    @Bean
    public InfluxDBClient influxDBClient() {
        logger.info("InfluxDB baglantisi kuruluyor: {}", url);
        try {
            influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
            logger.info("InfluxDB baglantisi basariyla kuruldu. Org: {}, Bucket: {}", org, bucket);
        } catch (Exception e) {
            logger.error("InfluxDB baglantisi kurulamadi: {}", e.getMessage());
            // Uygulama calissin, ancak veritabani islemleri hata verecek
            influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
        }
        return influxDBClient;
    }

    @Bean
    public String influxDBBucket() {
        return bucket;
    }

    @Bean
    public String influxDBOrg() {
        return org;
    }

    /**
     * Uygulama kapanirken baglantiyi guvenle kapatir.
     */
    @PreDestroy
    public void close() {
        if (influxDBClient != null) {
            logger.info("InfluxDB baglantisi kapatiliyor...");
            influxDBClient.close();
        }
    }
}
