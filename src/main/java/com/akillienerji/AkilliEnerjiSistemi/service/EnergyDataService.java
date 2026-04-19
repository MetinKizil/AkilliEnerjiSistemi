package com.akillienerji.AkilliEnerjiSistemi.service;

import com.akillienerji.AkilliEnerjiSistemi.model.DashboardSummary;
import com.akillienerji.AkilliEnerjiSistemi.model.EnergyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EnergyDataService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AlertService alertService;

    private double batteryLevel = 78.5;
    private final Random random = new Random();

    /**
     * Anlik enerji verisini dondurur (simule edilmis).
     * Gercek senaryoda InfluxDB'den okunur.
     */
    public EnergyData getRealtimeData() {
        double baseConsumption = deviceService.getTotalConsumption();
        double consumption = baseConsumption + (random.nextDouble() * 0.5 - 0.25);
        consumption = Math.max(0, consumption);

        int hour = LocalDateTime.now().getHour();
        double production = getSolarProductionForHour(hour);

        // Batarya seviyesi gunes uretimine gore degisir
        batteryLevel += (production - consumption) * 0.05;
        batteryLevel = Math.max(0, Math.min(100, batteryLevel));

        double cost = consumption * 2.18; // TL/kWh

        EnergyData data = new EnergyData();
        data.setTimestamp(LocalDateTime.now());
        data.setConsumption(round2(consumption));
        data.setProduction(round2(production));
        data.setBatteryLevel(round1(batteryLevel));
        data.setCost(round2(cost));
        return data;
    }

    /**
     * Gecmis enerji verilerini dondurur (simule edilmis).
     * Gercek senaryoda InfluxDB'den query ile cekilir.
     */
    public List<EnergyData> getHistoryData(int hours) {
        List<EnergyData> history = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = hours; i >= 0; i--) {
            EnergyData data = new EnergyData();
            LocalDateTime time = now.minusHours(i);
            data.setTimestamp(time);

            int hour = time.getHour();
            double baseConsumption = getBaseConsumptionForHour(hour);
            double consumption = baseConsumption + (random.nextDouble() * 1.0 - 0.5);
            consumption = Math.max(0, consumption);
            double production = getSolarProductionForHour(hour);

            data.setConsumption(round2(consumption));
            data.setProduction(round2(production));
            data.setBatteryLevel(round1(50 + random.nextDouble() * 40));
            data.setCost(round2(consumption * 2.18));

            history.add(data);
        }
        return history;
    }

    /**
     * Dashboard ozet verisini dondurur.
     */
    public DashboardSummary getSummary() {
        EnergyData realtime = getRealtimeData();
        DashboardSummary summary = new DashboardSummary();
        summary.setTotalConsumption(realtime.getConsumption());
        summary.setTotalProduction(realtime.getProduction());
        summary.setBatteryLevel(realtime.getBatteryLevel());
        summary.setDailyCost(realtime.getCost());
        summary.setActiveDevices((int) deviceService.getActiveDeviceCount());
        summary.setTotalDevices(deviceService.getTotalDeviceCount());
        summary.setActiveAlerts((int) alertService.getActiveAlertCount());
        return summary;
    }

    // Saate gore gercekci tuketim patterni
    private double getBaseConsumptionForHour(int hour) {
        if (hour >= 0 && hour < 6) return 1.2;    // Gece - dusuk tuketim
        if (hour >= 6 && hour < 9) return 3.5;    // Sabah - yuksek
        if (hour >= 9 && hour < 12) return 2.5;   // Ogle oncesi
        if (hour >= 12 && hour < 14) return 3.0;  // Ogle
        if (hour >= 14 && hour < 18) return 2.3;  // Ogleden sonra
        if (hour >= 18 && hour < 22) return 4.2;  // Aksam - en yuksek
        return 1.8;                                 // Gece geç
    }

    // Gunes paneli uretim simulasyonu
    private double getSolarProductionForHour(int hour) {
        if (hour < 6 || hour > 19) return 0;
        if (hour >= 6 && hour < 10) return (hour - 5) * 0.5;
        if (hour >= 10 && hour < 15) return 2.5 + random.nextDouble() * 0.8;
        if (hour >= 15 && hour < 20) return (20 - hour) * 0.4;
        return 0;
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
