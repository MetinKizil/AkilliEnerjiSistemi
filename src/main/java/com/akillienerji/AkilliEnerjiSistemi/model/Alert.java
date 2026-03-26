package com.akillienerji.AkilliEnerjiSistemi.model;

import java.time.LocalDateTime;

public class Alert {
    private String id;
    private String message;
    private String severity;
    private LocalDateTime timestamp;
    private boolean acknowledged;
    private String deviceId;

    public Alert() {}

    public Alert(String id, String message, String severity, LocalDateTime timestamp, boolean acknowledged, String deviceId) {
        this.id = id;
        this.message = message;
        this.severity = severity;
        this.timestamp = timestamp;
        this.acknowledged = acknowledged;
        this.deviceId = deviceId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean acknowledged) { this.acknowledged = acknowledged; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}
