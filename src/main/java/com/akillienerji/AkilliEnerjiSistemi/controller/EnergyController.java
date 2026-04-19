package com.akillienerji.AkilliEnerjiSistemi.controller;

import com.akillienerji.AkilliEnerjiSistemi.service.NotificationService;
import com.akillienerji.AkilliEnerjiSistemi.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.akillienerji.AkilliEnerjiSistemi.service.EnergyDataService;
import com.akillienerji.AkilliEnerjiSistemi.model.EnergyData;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/energy")
public class EnergyController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private EnergyDataService energyDataService;

    /**
     * Enerji tuketimi kontrol et
     * POST /api/energy/check?consumption=XXX
     */
    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkData(@RequestParam double consumption) {
        double limit = alertService.getThreshold().getMaxConsumption();

        notificationService.checkEnergyLimit(consumption, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("consumption", consumption);
        response.put("limit", limit);
        response.put("exceeded", consumption > limit);
        response.put("message", consumption > limit ?
            "Enerji limiti asildi! Alarm olusturuldu." :
            "Tuketim normal seviyede.");
        return ResponseEntity.ok(response);
    }

    /**
     * Anlik enerji tuketim verisini getir
     * GET /api/energy/data
     */
    @GetMapping("/data")
    public ResponseEntity<EnergyData> getEnergyData() {
        return ResponseEntity.ok(energyDataService.getRealtimeData());
    }
}