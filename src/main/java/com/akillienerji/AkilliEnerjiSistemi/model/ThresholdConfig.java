package com.akillienerji.AkilliEnerjiSistemi.model;

public class ThresholdConfig {
    private double maxConsumption;
    private double warningLevel;
    private double criticalLevel;

    public ThresholdConfig() {}

    public ThresholdConfig(double maxConsumption, double warningLevel, double criticalLevel) {
        this.maxConsumption = maxConsumption;
        this.warningLevel = warningLevel;
        this.criticalLevel = criticalLevel;
    }

    public double getMaxConsumption() { return maxConsumption; }
    public void setMaxConsumption(double maxConsumption) { this.maxConsumption = maxConsumption; }

    public double getWarningLevel() { return warningLevel; }
    public void setWarningLevel(double warningLevel) { this.warningLevel = warningLevel; }

    public double getCriticalLevel() { return criticalLevel; }
    public void setCriticalLevel(double criticalLevel) { this.criticalLevel = criticalLevel; }
}
