package com.akillienerji.AkilliEnerjiSistemi.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * InfluxDB'ye yazilacak sensor olcum modeli.
 * Zaman serisi veritabanina uygun yapilandirma.
 *
 * InfluxDB yapisi:
 *   Measurement: "enerji_tuketim" veya "sicaklik_olcum"
 *   Tags: cihaz, konum, tip
 *   Fields: deger (value)
 *   Timestamp: olcum zamani
 */
public class SensorReading {

    private String measurement;     // InfluxDB measurement adi
    private String deviceId;        // Cihaz kimlik numarasi
    private String sensorType;      // ENERGY, TEMPERATURE, HUMIDITY, MOTION
    private double value;           // Olcum degeri
    private String unit;            // WATT, CELSIUS, PERCENT
    private String location;        // Konum (bina/oda)
    private Instant timestamp;      // Olcum zamani (UTC)
    private Map<String, String> tags;   // Ek etiketler
    private Map<String, Object> fields; // Ek alanlar

    public SensorReading() {
        this.tags = new HashMap<>();
        this.fields = new HashMap<>();
        this.timestamp = Instant.now();
    }

    public SensorReading(String measurement, String deviceId, String sensorType,
                         double value, String unit, String location) {
        this();
        this.measurement = measurement;
        this.deviceId = deviceId;
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.location = location;
    }

    /**
     * Ek etiket ekler (InfluxDB tag).
     */
    public SensorReading addTag(String key, String value) {
        this.tags.put(key, value);
        return this;
    }

    /**
     * Ek alan ekler (InfluxDB field).
     */
    public SensorReading addField(String key, Object value) {
        this.fields.put(key, value);
        return this;
    }

    // Getters and Setters
    public String getMeasurement() { return measurement; }
    public void setMeasurement(String measurement) { this.measurement = measurement; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Map<String, String> getTags() { return tags; }
    public void setTags(Map<String, String> tags) { this.tags = tags; }

    public Map<String, Object> getFields() { return fields; }
    public void setFields(Map<String, Object> fields) { this.fields = fields; }

    @Override
    public String toString() {
        return "SensorReading{" +
                "measurement='" + measurement + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", sensorType='" + sensorType + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", location='" + location + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
