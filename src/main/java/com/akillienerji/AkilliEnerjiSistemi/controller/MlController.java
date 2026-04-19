package com.akillienerji.AkilliEnerjiSistemi.controller;

import com.akillienerji.AkilliEnerjiSistemi.dto.ApiResponseDTO;
import com.akillienerji.AkilliEnerjiSistemi.dto.EnergyForecastDTO;
import com.akillienerji.AkilliEnerjiSistemi.ml.AnomalyDetectionService;
import com.akillienerji.AkilliEnerjiSistemi.ml.EnergyForecastService;
import com.akillienerji.AkilliEnerjiSistemi.ml.EnergyOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Makine ogrenimi REST API controller'i.
 * Enerji tahmini, anomali tespiti ve optimizasyon endpoint'leri.
 */
@RestController
@RequestMapping("/api/ml")
public class MlController {

    @Autowired
    private EnergyForecastService forecastService;

    @Autowired
    private AnomalyDetectionService anomalyService;

    @Autowired
    private EnergyOptimizationService optimizationService;

    /**
     * Gelecek N saat icin enerji tuketim tahmini.
     * GET /api/ml/forecast?hours=6
     */
    @GetMapping("/forecast")
    public ResponseEntity<ApiResponseDTO<List<EnergyForecastDTO>>> getForecast(
            @RequestParam(defaultValue = "6") int hours) {
        List<EnergyForecastDTO> forecasts = forecastService.predictNextHours(hours);
        return ResponseEntity.ok(ApiResponseDTO.success(
                hours + " saatlik enerji tahmini", forecasts));
    }

    /**
     * ML model durumunu dondurur.
     * GET /api/ml/status
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("forecastModelTrained", forecastService.isModelTrained());
        status.put("forecastModelStats", forecastService.getModelStats());
        status.put("forecastLastTrain", forecastService.getLastTrainTime());
        status.put("anomalyStats", anomalyService.getStatsSummary());
        return ResponseEntity.ok(ApiResponseDTO.success("ML servis durumu", status));
    }

    /**
     * Belirli bir deger icin anomali kontrolu.
     * POST /api/ml/anomaly/check?value=5000
     */
    @PostMapping("/anomaly/check")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> checkAnomaly(
            @RequestParam double value) {
        AnomalyDetectionService.AnomalyResult result = anomalyService.detectAnomaly(value);

        Map<String, Object> response = new HashMap<>();
        response.put("value", value);
        response.put("isAnomaly", result.isAnomaly);
        response.put("zScore", Math.round(result.zScore * 100.0) / 100.0);
        response.put("expectedLower", Math.round(result.expectedLower * 100.0) / 100.0);
        response.put("expectedUpper", Math.round(result.expectedUpper * 100.0) / 100.0);

        String message = result.isAnomaly ? "Anomali tespit edildi!" : "Deger normal aralikta.";
        return ResponseEntity.ok(ApiResponseDTO.success(message, response));
    }

    /**
     * Enerji butcesi icin optimizasyon onerisi.
     * GET /api/ml/optimize?budget=3.0
     */
    @GetMapping("/optimize")
    public ResponseEntity<ApiResponseDTO<EnergyOptimizationService.OptimizationResult>> optimize(
            @RequestParam(defaultValue = "3.0") double budget) {
        EnergyOptimizationService.OptimizationResult result =
                optimizationService.optimizeForBudget(budget);
        return ResponseEntity.ok(ApiResponseDTO.success(result.message, result));
    }

    /**
     * Enerji tasarruf modu onerisi.
     * GET /api/ml/saving-mode
     */
    @GetMapping("/saving-mode")
    public ResponseEntity<ApiResponseDTO<EnergyOptimizationService.OptimizationResult>> savingMode() {
        EnergyOptimizationService.OptimizationResult result =
                optimizationService.suggestSavingMode();
        return ResponseEntity.ok(ApiResponseDTO.success(result.message, result));
    }

    /**
     * Yogun saat programi onerisi.
     * GET /api/ml/schedule?peakStart=18&peakEnd=22
     */
    @GetMapping("/schedule")
    public ResponseEntity<ApiResponseDTO<List<String>>> getSchedule(
            @RequestParam(defaultValue = "18") int peakStart,
            @RequestParam(defaultValue = "22") int peakEnd) {
        List<String> schedule = optimizationService.suggestSchedule(peakStart, peakEnd);
        return ResponseEntity.ok(ApiResponseDTO.success("Yogun saat programi onerisi", schedule));
    }
}
