package com.akillienerji.AkilliEnerjiSistemi.mqtt;

import com.akillienerji.AkilliEnerjiSistemi.dto.SensorDataDTO;
import com.akillienerji.AkilliEnerjiSistemi.model.SensorReading;
import com.akillienerji.AkilliEnerjiSistemi.repository.InfluxDBRepository;
import com.akillienerji.AkilliEnerjiSistemi.service.AlertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * MQTT mesaj isleyici.
 * Broker'dan gelen mesajlari alir, JSON olarak parse eder,
 * InfluxDB'ye yazar ve esik kontrolu yapar.
 *
 * Dinlenen topic'ler:
 *   enerji/#     -> Enerji tuketim verileri
 *   sicaklik/#   -> Sicaklik verileri
 *   cihaz/#      -> Cihaz durum bildirimleri
 */
@Component
public class MqttMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MqttMessageHandler.class);

    private final ObjectMapper objectMapper;

    @Autowired
    private InfluxDBRepository influxDBRepository;

    @Autowired
    private AlertService alertService;

    public MqttMessageHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * MQTT mesajlarini isler.
     * Spring Integration tarafindan mqttInputChannel uzerinden cagirilir.
     *
     * @param message Gelen MQTT mesaji
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = message.getPayload().toString();

        logger.info("MQTT mesaji alindi - Topic: {}, Payload: {}", topic, payload);

        try {
            // JSON payload'u SensorDataDTO'ya donustur
            SensorDataDTO sensorData = objectMapper.readValue(payload, SensorDataDTO.class);

            // SensorReading'e donustur
            SensorReading reading = convertToSensorReading(sensorData, topic);

            // InfluxDB'ye yaz
            influxDBRepository.writeSensorData(reading);
            logger.info("Sensor verisi kaydedildi: {} - {} {}",
                    sensorData.getDeviceId(), sensorData.getValue(), sensorData.getUnit());

            // Esik kontrolu
            checkThresholds(sensorData);

        } catch (Exception e) {
            logger.error("MQTT mesaj isleme hatasi - Topic: {}, Hata: {}", topic, e.getMessage());
        }
    }

    /**
     * SensorDataDTO'yu SensorReading modeline donusturur.
     * Topic yapisindan ek bilgi cikarir (bina, oda vb.)
     */
    private SensorReading convertToSensorReading(SensorDataDTO dto, String topic) {
        String measurement = determineMeasurement(dto.getType());

        SensorReading reading = new SensorReading(
                measurement,
                dto.getDeviceId(),
                dto.getType(),
                dto.getValue(),
                dto.getUnit(),
                dto.getLocation()
        );

        // Timestamp ayarla
        if (dto.getTimestamp() != null) {
            reading.setTimestamp(dto.getTimestamp().atZone(java.time.ZoneId.systemDefault()).toInstant());
        } else {
            reading.setTimestamp(Instant.now());
        }

        // Topic'ten konum bilgisi cikar (orn: enerji/bina1/oda5)
        String[] topicParts = topic.split("/");
        if (topicParts.length >= 3) {
            reading.addTag("bina", topicParts[1]);
            reading.addTag("oda", topicParts[2]);
            if (reading.getLocation() == null || reading.getLocation().isEmpty()) {
                reading.setLocation(topicParts[1] + "/" + topicParts[2]);
            }
        }

        return reading;
    }

    /**
     * Sensor tipine gore InfluxDB measurement adini belirler.
     */
    private String determineMeasurement(String sensorType) {
        return switch (sensorType.toUpperCase()) {
            case "ENERGY" -> "enerji_tuketim";
            case "TEMPERATURE" -> "sicaklik_olcum";
            case "HUMIDITY" -> "nem_olcum";
            case "MOTION" -> "hareket_algilama";
            default -> "genel_olcum";
        };
    }

    /**
     * Gelen veriyi esik degerleriyle karsilastirir.
     * Esik asilirsa alarm olusturur.
     */
    private void checkThresholds(SensorDataDTO data) {
        if ("ENERGY".equalsIgnoreCase(data.getType())) {
            double threshold = alertService.getThreshold().getMaxConsumption();
            double warningLevel = alertService.getThreshold().getWarningLevel();

            if (data.getValue() > threshold) {
                String msg = String.format("KRITIK: %s cihazi enerji esigini asti! Deger: %.1f W, Esik: %.1f W",
                        data.getDeviceId(), data.getValue(), threshold);
                alertService.createAlert(msg, "CRITICAL", data.getDeviceId());
                logger.warn(msg);
            } else if (data.getValue() > warningLevel) {
                String msg = String.format("UYARI: %s cihazi uyari seviyesine yaklasti. Deger: %.1f W, Uyari: %.1f W",
                        data.getDeviceId(), data.getValue(), warningLevel);
                alertService.createAlert(msg, "WARNING", data.getDeviceId());
                logger.warn(msg);
            }
        }
    }
}
