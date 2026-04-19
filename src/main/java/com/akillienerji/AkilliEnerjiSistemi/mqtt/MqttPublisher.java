package com.akillienerji.AkilliEnerjiSistemi.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * MQTT mesaj gonderici.
 * Cihazlara MQTT uzerinden komut gondermek icin kullanilir.
 *
 * Ornek kullanim:
 *   mqttPublisher.publish("cihaz/dev1/komut", "{\"action\":\"TURN_OFF\"}");
 *
 * Topic yapisi:
 *   cihaz/{cihazId}/komut - Cihaza gonderilen komutlar
 */
@Component
public class MqttPublisher {

    private static final Logger logger = LoggerFactory.getLogger(MqttPublisher.class);

    @Autowired
    private MqttPahoMessageHandler mqttOutbound;

    /**
     * Belirtilen topic'e mesaj gonderir.
     *
     * @param topic   Hedef MQTT topic
     * @param payload Gonderilecek mesaj (JSON string)
     */
    public void publish(String topic, String payload) {
        try {
            Message<String> message = MessageBuilder
                    .withPayload(payload)
                    .setHeader(MqttHeaders.TOPIC, topic)
                    .setHeader(MqttHeaders.QOS, 1)
                    .build();

            mqttOutbound.handleMessage(message);
            logger.info("MQTT mesaj gonderildi - Topic: {}, Payload: {}", topic, payload);
        } catch (Exception e) {
            logger.error("MQTT mesaj gonderme hatasi - Topic: {}, Hata: {}", topic, e.getMessage());
        }
    }

    /**
     * Bir cihaza acma/kapama komutu gonderir.
     *
     * @param deviceId Hedef cihaz ID
     * @param turnOn   true: ac, false: kapat
     */
    public void sendDeviceCommand(String deviceId, boolean turnOn) {
        String topic = "cihaz/" + deviceId + "/komut";
        String action = turnOn ? "TURN_ON" : "TURN_OFF";
        String payload = String.format("{\"action\":\"%s\",\"deviceId\":\"%s\"}", action, deviceId);
        publish(topic, payload);
    }

    /**
     * Bir cihaza ozel komut gonderir.
     *
     * @param deviceId Hedef cihaz ID
     * @param command  Komut adi (orn: SET_TEMPERATURE)
     * @param value    Komut degeri (orn: 22.5)
     */
    public void sendDeviceCommand(String deviceId, String command, double value) {
        String topic = "cihaz/" + deviceId + "/komut";
        String payload = String.format(
                "{\"action\":\"%s\",\"deviceId\":\"%s\",\"value\":%.1f}",
                command, deviceId, value
        );
        publish(topic, payload);
    }
}
