package com.akillienerji.AkilliEnerjiSistemi.service;

import com.akillienerji.AkilliEnerjiSistemi.model.Alert;
import com.akillienerji.AkilliEnerjiSistemi.model.ThresholdConfig;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final Map<String, Alert> alerts = new ConcurrentHashMap<>();
    private ThresholdConfig thresholdConfig;

    @PostConstruct
    public void init() {
        thresholdConfig = new ThresholdConfig(500.0, 400.0, 600.0);

        createAlert("Klima enerji tuketimi esik degerini asti!", "WARNING", "dev1");
        createAlert("Genel tuketim kritik seviyeye ulasti!", "CRITICAL", null);

        // Eski onaylanmis bir alert
        Alert oldAlert = new Alert(
            UUID.randomUUID().toString(),
            "Su isitici uzun sure calisiyor",
            "INFO",
            LocalDateTime.now().minusHours(2),
            true,
            "dev3"
        );
        alerts.put(oldAlert.getId(), oldAlert);
    }

    public List<Alert> getAllAlerts() {
        List<Alert> list = new ArrayList<>(alerts.values());
        list.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
        return list;
    }

    public List<Alert> getActiveAlerts() {
        return alerts.values().stream()
                .filter(a -> !a.isAcknowledged())
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .collect(Collectors.toList());
    }

    public Alert acknowledgeAlert(String id) {
        Alert alert = alerts.get(id);
        if (alert != null) {
            alert.setAcknowledged(true);
        }
        return alert;
    }

    public void createAlert(String message, String severity, String deviceId) {
        Alert alert = new Alert(
            UUID.randomUUID().toString(),
            message,
            severity,
            LocalDateTime.now(),
            false,
            deviceId
        );
        alerts.put(alert.getId(), alert);
    }

    public ThresholdConfig getThreshold() {
        return thresholdConfig;
    }

    public ThresholdConfig updateThreshold(ThresholdConfig config) {
        this.thresholdConfig = config;
        return thresholdConfig;
    }

    public long getActiveAlertCount() {
        return alerts.values().stream().filter(a -> !a.isAcknowledged()).count();
    }
}
