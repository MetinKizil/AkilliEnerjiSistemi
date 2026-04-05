package com.akillienerji.AkilliEnerjiSistemi.dto;

import java.time.LocalDateTime;

/**
 * Standart API yanitl sarmalayici.
 * Tum REST API yanitlari bu format uzerinden doner.
 *
 * Basarili yanit ornegi:
 * {
 *   "success": true,
 *   "message": "Islem basarili",
 *   "data": { ... },
 *   "timestamp": "2026-04-05T20:30:00"
 * }
 */
public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponseDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Basarili yanit olusturucu.
     */
    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<>(true, message, data);
    }

    /**
     * Basarili yanit (sadece mesaj).
     */
    public static <T> ApiResponseDTO<T> success(String message) {
        return new ApiResponseDTO<>(true, message, null);
    }

    /**
     * Hata yaniti olusturucu.
     */
    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<>(false, message, null);
    }

    /**
     * Hata yaniti (veriyle birlikte).
     */
    public static <T> ApiResponseDTO<T> error(String message, T data) {
        return new ApiResponseDTO<>(false, message, data);
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
