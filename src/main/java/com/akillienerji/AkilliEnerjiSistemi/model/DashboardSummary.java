package com.akillienerji.AkilliEnerjiSistemi.model;

public class DashboardSummary {
    private double totalConsumption;
    private double totalProduction;
    private double batteryLevel;
    private double dailyCost;
    private int activeDevices;
    private int totalDevices;
    private int activeAlerts;

    public DashboardSummary() {}

    public double getTotalConsumption() { return totalConsumption; }
    public void setTotalConsumption(double totalConsumption) { this.totalConsumption = totalConsumption; }

    public double getTotalProduction() { return totalProduction; }
    public void setTotalProduction(double totalProduction) { this.totalProduction = totalProduction; }

    public double getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(double batteryLevel) { this.batteryLevel = batteryLevel; }

    public double getDailyCost() { return dailyCost; }
    public void setDailyCost(double dailyCost) { this.dailyCost = dailyCost; }

    public int getActiveDevices() { return activeDevices; }
    public void setActiveDevices(int activeDevices) { this.activeDevices = activeDevices; }

    public int getTotalDevices() { return totalDevices; }
    public void setTotalDevices(int totalDevices) { this.totalDevices = totalDevices; }

    public int getActiveAlerts() { return activeAlerts; }
    public void setActiveAlerts(int activeAlerts) { this.activeAlerts = activeAlerts; }
}
