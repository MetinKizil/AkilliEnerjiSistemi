package com.akillienerji.AkilliEnerjiSistemi.service;

import com.akillienerji.AkilliEnerjiSistemi.model.Device;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeviceService {

    private final Map<String, Device> devices = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        addDevice(new Device("dev1", "Klima", "HVAC", true, 2.5, "Salon"));
        addDevice(new Device("dev2", "Aydinlatma", "LIGHTING", true, 0.1, "Salon"));
        addDevice(new Device("dev3", "Su Isitici", "HEATER", false, 3.0, "Mutfak"));
        addDevice(new Device("dev4", "Buzdolabi", "APPLIANCE", true, 0.15, "Mutfak"));
        addDevice(new Device("dev5", "Camasir Makinesi", "APPLIANCE", false, 2.0, "Banyo"));
        addDevice(new Device("dev6", "Televizyon", "ENTERTAINMENT", true, 0.12, "Salon"));
        addDevice(new Device("dev7", "Elektrikli Soba", "HEATER", false, 2.8, "Yatak Odasi"));
        addDevice(new Device("dev8", "Bilgisayar", "ENTERTAINMENT", true, 0.35, "Calisma Odasi"));
    }

    private void addDevice(Device device) {
        devices.put(device.getId(), device);
    }

    public List<Device> getAllDevices() {
        return new ArrayList<>(devices.values());
    }

    public Device getDevice(String id) {
        return devices.get(id);
    }

    public Device toggleDevice(String id) {
        Device device = devices.get(id);
        if (device != null) {
            device.setActive(!device.isActive());
        }
        return device;
    }

    public Device updateDevice(String id, Device updated) {
        Device device = devices.get(id);
        if (device != null) {
            if (updated.getName() != null) device.setName(updated.getName());
            if (updated.getLocation() != null) device.setLocation(updated.getLocation());
            if (updated.getPowerConsumption() > 0) device.setPowerConsumption(updated.getPowerConsumption());
        }
        return device;
    }

    public long getActiveDeviceCount() {
        return devices.values().stream().filter(Device::isActive).count();
    }

    public int getTotalDeviceCount() {
        return devices.size();
    }

    public double getTotalConsumption() {
        return devices.values().stream()
                .filter(Device::isActive)
                .mapToDouble(Device::getPowerConsumption)
                .sum();
    }
}
