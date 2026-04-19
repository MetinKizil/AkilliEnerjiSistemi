package com.akillienerji.AkilliEnerjiSistemi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Zamanlanmis gorev konfigurasyonu.
 * Periyodik islemler icin @Scheduled anotasyonunu aktiflestirir.
 *
 * Kullanim alanlari:
 *   - ML model guncelleme (her 6 saatte bir)
 *   - Anomali tespiti kontrolu (her 5 dakikada bir)
 *   - InfluxDB veri ozetleme (her gece yarisi)
 *   - WebSocket uzerinden anlik veri push (her 5 saniyede bir)
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
    // @EnableScheduling yeterli — ek konfigürasyona gerek yok.
    // Thread pool özelleştirmesi gerekirse burada TaskScheduler bean'i tanımlanır.
}
