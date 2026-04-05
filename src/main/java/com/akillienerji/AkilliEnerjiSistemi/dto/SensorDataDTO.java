package com.akillienerji.AkilliEnerjiSistemi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * MQTT'den gelen sensor verisi DTO'su.
 * Ornek JSON:
 * {
 *   "deviceId": "dev1",
 *   "type": "ENERGY",
 *   "value": 3200.0,
 *   "unit": "WATT",
 *   "timestamp": "2026-04-05T20:30:00"
 * }
 */
public class SensorDataDTO {

    @NotBlank(message = "Cihaz ID bos olamaz")
    private String deviceId;

    @NotBlank(message = "Sensor tipi bos olamaz")
    private String type; // ENERGY, TEMPERATURE, HUMIDITY, MOTION

    @NotNull(message = "Deger bos olamaz")
    @Positive(message = "Deger pozitif olmali")
    private Double value;

    @NotBlank(message = "Birim bos olamaz")
    private String unit; // WATT, CELSIUS, PERCENT, BOOLEAN

    private LocalDateTime timestamp;

    private String location; // Opsiyonel: bina/oda bilgisi

    public SensorDataDTO() {}

    public SensorDataDTO(String deviceId, String type, Double value, String unit, LocalDateTime timestamp) {
        this.deviceId = deviceId;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public String toString() {
        return "SensorDataDTO{" +
                "deviceId='" + deviceId + '\'' +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
