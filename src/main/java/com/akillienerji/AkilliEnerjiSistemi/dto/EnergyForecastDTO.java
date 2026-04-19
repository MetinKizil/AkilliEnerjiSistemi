package com.akillienerji.AkilliEnerjiSistemi.dto;

import java.time.LocalDateTime;

/**
 * Enerji tahmin sonucu DTO'su.
 * ML servislerinden donen tahmin verisini tasir.
 */
public class EnergyForecastDTO {

    private LocalDateTime forecastTime;
    private double predictedConsumption;  // kW
    private double confidenceLower;       // Alt guven araligi
    private double confidenceUpper;       // Ust guven araligi
    private double confidencePercentage;  // Guven yuzdesi (orn: %95)
    private String modelType;            // LINEAR_REGRESSION, RANDOM_FOREST

    public EnergyForecastDTO() {}

    public EnergyForecastDTO(LocalDateTime forecastTime, double predictedConsumption,
                             double confidenceLower, double confidenceUpper,
                             double confidencePercentage, String modelType) {
        this.forecastTime = forecastTime;
        this.predictedConsumption = predictedConsumption;
        this.confidenceLower = confidenceLower;
        this.confidenceUpper = confidenceUpper;
        this.confidencePercentage = confidencePercentage;
        this.modelType = modelType;
    }

    // Getters and Setters
    public LocalDateTime getForecastTime() { return forecastTime; }
    public void setForecastTime(LocalDateTime forecastTime) { this.forecastTime = forecastTime; }

    public double getPredictedConsumption() { return predictedConsumption; }
    public void setPredictedConsumption(double predictedConsumption) { this.predictedConsumption = predictedConsumption; }

    public double getConfidenceLower() { return confidenceLower; }
    public void setConfidenceLower(double confidenceLower) { this.confidenceLower = confidenceLower; }

    public double getConfidenceUpper() { return confidenceUpper; }
    public void setConfidenceUpper(double confidenceUpper) { this.confidenceUpper = confidenceUpper; }

    public double getConfidencePercentage() { return confidencePercentage; }
    public void setConfidencePercentage(double confidencePercentage) { this.confidencePercentage = confidencePercentage; }

    public String getModelType() { return modelType; }
    public void setModelType(String modelType) { this.modelType = modelType; }
}
