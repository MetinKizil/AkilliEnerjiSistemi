package com.akillienerji.AkilliEnerjiSistemi.model;

import java.time.LocalDateTime;

public class EnergyData {
    private LocalDateTime timestamp;
    private double consumption;
    private double production;
    private double batteryLevel;
    private double cost;

    public EnergyData() {}

    public EnergyData(LocalDateTime timestamp, double consumption, double production, double batteryLevel, double cost) {
        this.timestamp = timestamp;
        this.consumption = consumption;
        this.production = production;
        this.batteryLevel = batteryLevel;
        this.cost = cost;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public double getConsumption() { return consumption; }
    public void setConsumption(double consumption) { this.consumption = consumption; }

    public double getProduction() { return production; }
    public void setProduction(double production) { this.production = production; }

    public double getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(double batteryLevel) { this.batteryLevel = batteryLevel; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}
