package com.akillienerji.AkilliEnerjiSistemi.model;

public class Device {
    private String id;
    private String name;
    private String type;
    private boolean active;
    private double powerConsumption;
    private String location;

    public Device() {}

    public Device(String id, String name, String type, boolean active, double powerConsumption, String location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.active = active;
        this.powerConsumption = powerConsumption;
        this.location = location;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public double getPowerConsumption() { return powerConsumption; }
    public void setPowerConsumption(double powerConsumption) { this.powerConsumption = powerConsumption; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
