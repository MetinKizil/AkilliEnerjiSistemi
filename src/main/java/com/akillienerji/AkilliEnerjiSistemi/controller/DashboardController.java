package com.akillienerji.AkilliEnerjiSistemi.controller;

import com.akillienerji.AkilliEnerjiSistemi.model.DashboardSummary;
import com.akillienerji.AkilliEnerjiSistemi.model.EnergyData;
import com.akillienerji.AkilliEnerjiSistemi.service.EnergyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private EnergyDataService energyDataService;

    /**
     * Dashboard ozet verisi (KPI kartlari icin)
     * GET /api/dashboard/summary
     */
    @GetMapping("/summary")
    public DashboardSummary getSummary() {
        return energyDataService.getSummary();
    }

    /**
     * Anlik enerji verisi
     * GET /api/dashboard/realtime
     */
    @GetMapping("/realtime")
    public EnergyData getRealtimeData() {
        return energyDataService.getRealtimeData();
    }

    /**
     * Gecmis enerji verileri (grafik icin)
     * GET /api/dashboard/history?hours=24
     */
    @GetMapping("/history")
    public List<EnergyData> getHistoryData(@RequestParam(defaultValue = "24") int hours) {
        return energyDataService.getHistoryData(hours);
    }
}
