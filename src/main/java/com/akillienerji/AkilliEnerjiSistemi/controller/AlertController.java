package com.akillienerji.AkilliEnerjiSistemi.controller;

import com.akillienerji.AkilliEnerjiSistemi.model.Alert;
import com.akillienerji.AkilliEnerjiSistemi.model.ThresholdConfig;
import com.akillienerji.AkilliEnerjiSistemi.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    /**
     * Alarmlari listele
     * GET /api/alerts?activeOnly=false
     */
    @GetMapping
    public List<Alert> getAlerts(@RequestParam(defaultValue = "false") boolean activeOnly) {
        return activeOnly ? alertService.getActiveAlerts() : alertService.getAllAlerts();
    }

    /**
     * Alarmi onayla/kapat
     * POST /api/alerts/{id}/acknowledge
     */
    @PostMapping("/{id}/acknowledge")
    public ResponseEntity<Alert> acknowledgeAlert(@PathVariable String id) {
        Alert alert = alertService.acknowledgeAlert(id);
        return alert != null ? ResponseEntity.ok(alert) : ResponseEntity.notFound().build();
    }

    /**
     * Mevcut esik degerini al
     * GET /api/alerts/threshold
     */
    @GetMapping("/threshold")
    public ThresholdConfig getThreshold() {
        return alertService.getThreshold();
    }

    /**
     * Esik degerini guncelle (Admin yetkisi gerekli)
     * PUT /api/alerts/threshold
     */
    @PutMapping("/threshold")
    public ThresholdConfig updateThreshold(@RequestBody ThresholdConfig config) {
        return alertService.updateThreshold(config);
    }
}
