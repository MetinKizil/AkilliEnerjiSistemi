package com.akillienerji.AkilliEnerjiSistemi;

import com.akillienerji.AkilliEnerjiSistemi.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private AlertService alertService;

    public void sendAlert(String message) {
        System.out.println("🚨 KRİTİK UYARI BİLDİRİMİ GÖNDERİLDİ 🚨");
        System.out.println("Mesaj: " + message);
    }

    public void checkEnergyLimit(double currentConsumption, double limit) {
        if (currentConsumption > limit) {
            String alertMessage = "Dikkat! Anlik enerji tuketimi (" + currentConsumption +
                                  " kW) belirlenen limiti (" + limit + " kW) asti!";
            sendAlert(alertMessage);
            alertService.createAlert(alertMessage, "CRITICAL", null);
        }
    }
}