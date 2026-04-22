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
     * Yeni topic standardi: enerji/{lokasyon_id}/{oda_id}/{cihaz_turu}/{cihaz_id}/{veri_tipi}
     * Yeni payload standardi: {"cihazId":"...", "zamanDamgasi":..., "metrikler":{...}}
     *
     * @param message Gelen MQTT mesaji
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = message.getPayload().toString();

        logger.info("MQTT mesaji alindi - Topic: {}, Payload: {}", topic, payload);

        try {
            if (topic != null && topic.startsWith("enerji/")) {
                com.fasterxml.jackson.databind.JsonNode rootNode = objectMapper.readTree(payload);
                
                // Payload'dan temel bilgileri cikar
                String cihazId = rootNode.has("cihazId") ? rootNode.get("cihazId").asText() : "unknown";
                long zamanDamgasi = rootNode.has("zamanDamgasi") ? rootNode.get("zamanDamgasi").asLong() : System.currentTimeMillis();
                
                com.fasterxml.jackson.databind.JsonNode metrikler = rootNode.get("metrikler");
                
                if (metrikler != null && metrikler.isObject()) {
                    // Ana metrik (ornek: aktifGuc) value olarak atanir
                    double aktifGuc = metrikler.has("aktifGuc") ? metrikler.get("aktifGuc").asDouble() : 
                                      (metrikler.has("sicaklik") ? metrikler.get("sicaklik").asDouble() : 0.0);
                    
                    SensorReading reading = convertToSensorReading(topic, cihazId, zamanDamgasi, aktifGuc, metrikler);

                    // InfluxDB'ye yaz
                    influxDBRepository.writeSensorData(reading);
                    logger.info("Sensor verisi kaydedildi: {} - {} {}", reading.getDeviceId(), reading.getValue(), reading.getUnit());

                    // Esik kontrolu (AlertService icinde SensorDataDTO bekledigi icin mock DTO olusturuyoruz)
                    SensorDataDTO mockDto = new SensorDataDTO(cihazId, reading.getSensorType(), aktifGuc, reading.getUnit(), null);
                    checkThresholds(mockDto);
                }
            } else {
                logger.info("Bilinmeyen topic: {}. Mesaj atlandi.", topic);
            }
        } catch (Exception e) {
            logger.error("MQTT mesaj isleme hatasi - Topic: {}, Hata: {}", topic, e.getMessage(), e);
        }
    }

    /**
     * Yeni topic ve payload yapisini SensorReading modeline donusturur.
     */
    private SensorReading convertToSensorReading(String topic, String cihazId, long zamanDamgasi, double anaDeger, com.fasterxml.jackson.databind.JsonNode metrikler) {
        SensorReading reading = new SensorReading();
        reading.setDeviceId(cihazId);
        reading.setTimestamp(Instant.ofEpochMilli(zamanDamgasi));
        reading.setValue(anaDeger);
        
        // Topic parse: enerji/{lokasyon_id}/{oda_id}/{cihaz_turu}/{cihaz_id}/{veri_tipi}
        String[] parts = topic.split("/");
        if (parts.length >= 6) {
            reading.setLocation(parts[1] + "/" + parts[2]);
            reading.addTag("lokasyon", parts[1]);
            reading.addTag("oda", parts[2]);
            reading.addTag("cihaz_turu", parts[3]);
            reading.setMeasurement(determineMeasurement(parts[3]));
            
            // Cihaz turune gore sensor_type ve unit belirle
            if ("klima".equalsIgnoreCase(parts[3]) || "aydinlatma".equalsIgnoreCase(parts[3]) || "sayac".equalsIgnoreCase(parts[3])) {
                reading.setSensorType("ENERGY");
                reading.setUnit("WATT");
            } else if ("sensor".equalsIgnoreCase(parts[3])) {
                if ("sicaklik".equalsIgnoreCase(parts[5])) {
                    reading.setSensorType("TEMPERATURE");
                    reading.setUnit("CELSIUS");
                } else if ("nem".equalsIgnoreCase(parts[5])) {
                    reading.setSensorType("HUMIDITY");
                    reading.setUnit("PERCENT");
                } else {
                    reading.setSensorType("GENERAL");
                    reading.setUnit("UNKNOWN");
                }
            }
        } else {
            reading.setLocation("unknown");
            reading.setMeasurement("genel_olcum");
            reading.setSensorType("ENERGY");
            reading.setUnit("WATT");
        }

        // Diger metrikleri field olarak ekle
        metrikler.fieldNames().forEachRemaining(fieldName -> {
            if (!"aktifGuc".equals(fieldName) && !"sicaklik".equals(fieldName)) {
                reading.addField(fieldName, metrikler.get(fieldName).asDouble());
            }
        });

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
