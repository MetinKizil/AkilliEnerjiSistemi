package com.akillienerji.AkilliEnerjiSistemi.controller;

import com.akillienerji.AkilliEnerjiSistemi.model.Device;
import com.akillienerji.AkilliEnerjiSistemi.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * Tum cihazlari listele
     * GET /api/devices
     */
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    /**
     * Tek bir cihazin bilgisini getir
     * GET /api/devices/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable String id) {
        Device device = deviceService.getDevice(id);
        return device != null ? ResponseEntity.ok(device) : ResponseEntity.notFound().build();
    }

    /**
     * Cihazi ac/kapa (Toggle butonu)
     * POST /api/devices/{id}/toggle
     */
    @PostMapping("/{id}/toggle")
    public ResponseEntity<Device> toggleDevice(@PathVariable String id) {
        Device device = deviceService.toggleDevice(id);
        return device != null ? ResponseEntity.ok(device) : ResponseEntity.notFound().build();
    }

    /**
     * Cihaz bilgisini guncelle
     * PUT /api/devices/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable String id, @RequestBody Device device) {
        Device updated = deviceService.updateDevice(id, device);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
