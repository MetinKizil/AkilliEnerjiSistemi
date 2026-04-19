package com.akillienerji.AkilliEnerjiSistemi.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * MQTT baglanti ve kanal konfigurasyonu.
 * Eclipse Paho istemcisini Spring Integration ile entegre eder.
 *
 * Topic yapisi:
 *   enerji/{binaId}/{odaId}    - Enerji tuketim verileri
 *   sicaklik/{binaId}/{odaId}  - Sicaklik/nem verileri
 *   cihaz/{cihazId}/durum      - Cihaz durum bilgileri
 *   cihaz/{cihazId}/komut      - Cihazlara gonderilen komutlar
 */
@Configuration
public class MqttConfig {

    private static final Logger logger = LoggerFactory.getLogger(MqttConfig.class);

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.username:}")
    private String username;

    @Value("${mqtt.password:}")
    private String password;

    @Value("${mqtt.topics}")
    private String[] topics;

    @Value("${mqtt.qos:1}")
    private int qos;

    /**
     * MQTT istemci fabrikasi.
     * Broker baglanti ayarlarini yapilandirir.
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);

        if (username != null && !username.isEmpty()) {
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }

        factory.setConnectionOptions(options);
        logger.info("MQTT Client Factory olusturuldu. Broker: {}", brokerUrl);
        return factory;
    }

    /**
     * Gelen MQTT mesajlari icin kanal.
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT mesajlarini dinleyen adapter.
     * Belirlenen topic'lere abone olur.
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId + "-inbound",
                        mqttClientFactory(),
                        topics
                );
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(qos);
        adapter.setOutputChannel(mqttInputChannel());
        adapter.setAutoStartup(false); // Manuel baslatma — broker yoksa hata vermez
        logger.info("MQTT Inbound Adapter yapilandirildi. Topics: {}", String.join(", ", topics));
        return adapter;
    }

    /**
     * Giden MQTT mesajlari icin kanal.
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT mesaj gonderme handler'i.
     * Cihazlara komut gondermek icin kullanilir.
     */
    @Bean
    public MqttPahoMessageHandler mqttOutbound() {
        MqttPahoMessageHandler handler =
                new MqttPahoMessageHandler(clientId + "-outbound", mqttClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic("cihaz/komut");
        handler.setDefaultQos(qos);
        logger.info("MQTT Outbound Handler yapilandirildi.");
        return handler;
    }
}
