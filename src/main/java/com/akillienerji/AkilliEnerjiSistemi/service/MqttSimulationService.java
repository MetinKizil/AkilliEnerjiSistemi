package com.akillienerji.AkilliEnerjiSistemi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MqttSimulationService {

    private static final Logger logger = LoggerFactory.getLogger(MqttSimulationService.class);

    @Autowired
    @Qualifier("mqttOutboundChannel")
    private MessageChannel mqttOutboundChannel;

    @Value("${app.simulation-mode:false}")
    private boolean simulationMode;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    @Scheduled(fixedRate = 10000) // Her 10 saniyede bir
    public void simulateSensorData() {
        if (!simulationMode) {
            return;
        }

        try {
            String topic = "enerji/bina1/zeminKat/klima/cihaz01/tuketim";

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.put("cihazId", "cihaz01");
            rootNode.put("zamanDamgasi", System.currentTimeMillis());

            ObjectNode metrikler = rootNode.putObject("metrikler");

            // Rastgele tuketim verisi
            double aktifGuc = 450.0 + random.nextDouble() * 150.0; // 450 - 600 W
            metrikler.put("akim", 2.0 + random.nextDouble());
            metrikler.put("voltaj", 220.0 + random.nextDouble() * 2);
            metrikler.put("aktifGuc", aktifGuc);
            metrikler.put("frekans", 50.0 + random.nextDouble() * 0.2);

            String payload = rootNode.toString();

            Message<String> message = MessageBuilder.withPayload(payload)
                    .setHeader("mqtt_topic", topic)
                    .build();

            mqttOutboundChannel.send(message);

            logger.info("SIMULATOR: Veri gonderildi - Topic: {}, Payload: {}", topic, payload);
        } catch (Exception e) {
            logger.error("SIMULATOR: Veri gonderilirken hata olustu", e);
        }
    }
}
