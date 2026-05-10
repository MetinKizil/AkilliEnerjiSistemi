package com.akillienerji.AkilliEnerjiSistemi.ml;

import com.akillienerji.AkilliEnerjiSistemi.model.Device;
import com.akillienerji.AkilliEnerjiSistemi.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Enerji kaynak optimizasyon servisi.
 * Farkli enerji kaynaklarini en verimli sekilde kullanmayi amaclar.
 *
 * Algoritmalar:
 *   - Greedy Algorithm (mevcut): Oncelik sirasina gore cihaz yonetimi
 *   - Gelecekte: Linear Programming
 *   - Gelecekte: Genetic Algorithm
 *
 * Ozellikler:
 *   - Cihaz onceliklendirme (zorunlu vs opsiyonel)
 *   - Yuk dengeleme onerisi
 *   - Enerji tasarruf modu
 */
@Service
public class EnergyOptimizationService {

    private static final Logger logger = LoggerFactory.getLogger(EnergyOptimizationService.class);

    @Autowired
    private DeviceService deviceService;

    // Cihaz oncelik sirasi (dusuk sayi = yuksek oncelik)
    private static final Map<String, Integer> DEVICE_PRIORITY = Map.of(
            "APPLIANCE", 1,      // Buzdolabi vb. - kapatilmamali
            "LIGHTING", 2,       // Aydinlatma - gerekli
            "ENTERTAINMENT", 3,  // TV, bilgisayar - opsiyonel
            "HEATER", 4,         // Isitici - azaltilabilir
            "HVAC", 5            // Klima - en cok enerji tuketen
    );

    /**
     * Enerji butcesi dahilinde optimum cihaz kullanim onerisini hesaplar.
     * Greedy algoritma: En dusuk oncelikli (en cok enerji tuketen) cihazlari kapatir.
     *
     * @param maxPowerBudget Watt cinsinden maksimum enerji butcesi
     * @return Optimizasyon sonucu
     */
    public OptimizationResult optimizeForBudget(double maxPowerBudget) {
        List<Device> allDevices = deviceService.getAllDevices();
        List<Device> activeDevices = allDevices.parallelStream()
                .filter(Device::isActive)
                .collect(Collectors.toList());

        double currentConsumption = activeDevices.parallelStream()
                .mapToDouble(Device::getPowerConsumption)
                .sum();

        OptimizationResult result = new OptimizationResult();
        result.currentConsumption = currentConsumption;
        result.targetBudget = maxPowerBudget;

        if (currentConsumption <= maxPowerBudget) {
            result.optimized = false;
            result.message = "Mevcut tuketim butce dahilinde. Optimizasyon gerekli degil.";
            result.recommendedActions = Collections.emptyList();
            result.estimatedSavings = 0;
            return result;
        }

        // Oncelik sirasina gore sirala (yuksek sayi = dusuk oncelik = ilk kapatilacak)
        activeDevices.sort((a, b) -> {
            int priorityA = DEVICE_PRIORITY.getOrDefault(a.getType(), 3);
            int priorityB = DEVICE_PRIORITY.getOrDefault(b.getType(), 3);
            if (priorityA != priorityB) return Integer.compare(priorityB, priorityA);
            // Ayni oncelikteyse cok tuketen once kapatilsin
            return Double.compare(b.getPowerConsumption(), a.getPowerConsumption());
        });

        // Greedy: En dusuk oncelikliden baslayarak kapat
        double reducedConsumption = currentConsumption;
        List<String> actions = new ArrayList<>();
        List<String> devicesToTurnOff = new ArrayList<>();

        for (Device device : activeDevices) {
            if (reducedConsumption <= maxPowerBudget) break;

            int priority = DEVICE_PRIORITY.getOrDefault(device.getType(), 3);
            if (priority <= 1) {
                // Zorunlu cihazlari kapatma
                actions.add(String.format("KORUMA: %s (%s) kapatilmadi - zorunlu cihaz",
                        device.getName(), device.getType()));
                continue;
            }

            reducedConsumption -= device.getPowerConsumption();
            devicesToTurnOff.add(device.getId());
            actions.add(String.format("KAPAT: %s (%s) - %.2f kW tasarruf",
                    device.getName(), device.getType(), device.getPowerConsumption()));
        }

        result.optimized = true;
        result.optimizedConsumption = reducedConsumption;
        result.estimatedSavings = currentConsumption - reducedConsumption;
        result.recommendedActions = actions;
        result.devicesToTurnOff = devicesToTurnOff;
        result.message = String.format("Optimizasyon onerisi: %.2f kW -> %.2f kW (%.2f kW tasarruf)",
                currentConsumption, reducedConsumption, result.estimatedSavings);

        logger.info(result.message);
        return result;
    }

    /**
     * Enerji tasarruf modu.
     * Zorunlu olmayan tum cihazlari kapatma onerisi sunar.
     */
    public OptimizationResult suggestSavingMode() {
        return optimizeForBudget(0); // Minimum tuketim icin optimize et
    }

    /**
     * Belirli bir zaman dilimi icin optimal cihaz programi olusturur.
     *
     * @param peakHourStart Yoğun saat baslangic (orn: 18)
     * @param peakHourEnd   Yoğun saat bitis (orn: 22)
     * @return Program onerisi
     */
    public List<String> suggestSchedule(int peakHourStart, int peakHourEnd) {
        List<String> schedule = new ArrayList<>();
        List<Device> devices = deviceService.getAllDevices();

        schedule.add(String.format("=== Yogun Saat Programi (%02d:00 - %02d:00) ===",
                peakHourStart, peakHourEnd));

        for (Device device : devices) {
            int priority = DEVICE_PRIORITY.getOrDefault(device.getType(), 3);

            if (priority <= 1) {
                schedule.add(String.format("✅ %s: Her zaman acik (zorunlu)", device.getName()));
            } else if (priority == 2) {
                schedule.add(String.format("⚡ %s: Yogun saatte azaltilmis guc", device.getName()));
            } else if (priority >= 4) {
                schedule.add(String.format("⏰ %s: Yogun saat disinda calistir", device.getName()));
            } else {
                schedule.add(String.format("⚠️ %s: Yogun saatte kapatilabilir", device.getName()));
            }
        }

        return schedule;
    }

    /**
     * Optimizasyon sonucu.
     */
    public static class OptimizationResult {
        public boolean optimized;
        public String message;
        public double currentConsumption;
        public double optimizedConsumption;
        public double targetBudget;
        public double estimatedSavings;
        public List<String> recommendedActions;
        public List<String> devicesToTurnOff;
    }
}
