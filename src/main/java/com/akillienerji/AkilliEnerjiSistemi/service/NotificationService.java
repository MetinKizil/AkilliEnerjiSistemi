package com.akillienerji.AkilliEnerjiSistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Bildirim servisi.
 * Enerji esik degerleri asildiginda uyari bildirimleri gonderir.
 *
 * Gelecekte:
 *   - E-posta bildirimleri (Spring Mail)
 *   - WebSocket uzerinden anlik bildirim
 *   - SMS bildirimleri
 */
@Service
public class NotificationService {

    @Autowired
    private AlertService alertService;

    /**
     * Konsola uyari bildirimi gonderir.
     * Gelecekte e-posta ve push bildirim destegi eklenecek.
     *
     * @param message Uyari mesaji
     */
    public void sendAlert(String message) {
        System.out.println("🚨 KRİTİK UYARI BİLDİRİMİ GÖNDERİLDİ 🚨");
        System.out.println("Mesaj: " + message);
    }

    /**
     * Enerji limiti kontrolu.
     * Tuketim limiti asarsa alarm olusturur.
     *
     * @param currentConsumption Anlik tuketim (kW)
     * @param limit              Esik degeri (kW)
     */
    public void checkEnergyLimit(double currentConsumption, double limit) {
        if (currentConsumption > limit) {
            String alertMessage = "Dikkat! Anlik enerji tuketimi (" + currentConsumption +
                                  " kW) belirlenen limiti (" + limit + " kW) asti!";
            sendAlert(alertMessage);
            alertService.createAlert(alertMessage, "CRITICAL", null);
        }
    }
}
