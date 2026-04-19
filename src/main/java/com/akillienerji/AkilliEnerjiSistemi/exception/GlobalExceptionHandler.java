package com.akillienerji.AkilliEnerjiSistemi.exception;

import com.akillienerji.AkilliEnerjiSistemi.dto.ApiResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

/**
 * Merkezi hata yonetim sinifi.
 * Tum controller'lardan firlatilan hatalari yakalar
 * ve standart ApiResponseDTO formatinda yanit doner.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Cihaz bulunamadi hatasi.
     */
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDeviceNotFound(DeviceNotFoundException ex) {
        logger.warn("Cihaz bulunamadi: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDTO.error(ex.getMessage()));
    }

    /**
     * MQTT baglanti hatasi.
     */
    @ExceptionHandler(MqttConnectionException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleMqttConnection(MqttConnectionException ex) {
        logger.error("MQTT baglanti hatasi: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponseDTO.error("MQTT servisi kullanilabilir degil: " + ex.getMessage()));
    }

    /**
     * InfluxDB hatasi.
     */
    @ExceptionHandler(InfluxDBException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInfluxDB(InfluxDBException ex) {
        logger.error("InfluxDB hatasi: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponseDTO.error("Veritabani servisi kullanilabilir degil: " + ex.getMessage()));
    }

    /**
     * Validation hatalari (DTO dogrulama).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        logger.warn("Dogrulama hatasi: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDTO.error("Dogrulama hatasi: " + errors));
    }

    /**
     * Genel hata yakalayici.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleGeneral(Exception ex) {
        logger.error("Beklenmeyen hata: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDTO.error("Sunucu hatasi: " + ex.getMessage()));
    }

    // ---- Custom Exception Siniflari ----

    /**
     * Cihaz bulunamadi istisnasi.
     */
    public static class DeviceNotFoundException extends RuntimeException {
        public DeviceNotFoundException(String deviceId) {
            super("Cihaz bulunamadi: " + deviceId);
        }
    }

    /**
     * MQTT baglanti istisnasi.
     */
    public static class MqttConnectionException extends RuntimeException {
        public MqttConnectionException(String message) {
            super(message);
        }

        public MqttConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * InfluxDB istisnasi.
     */
    public static class InfluxDBException extends RuntimeException {
        public InfluxDBException(String message) {
            super(message);
        }

        public InfluxDBException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
