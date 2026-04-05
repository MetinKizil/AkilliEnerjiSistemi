package com.akillienerji.AkilliEnerjiSistemi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket konfigurasyonu.
 * STOMP protokolu uzerinden gercek zamanli veri akisi saglar.
 *
 * Frontend baglanti: ws://localhost:8080/ws
 * Abone olunacak kanallar:
 *   /topic/energy    - Anlik enerji verileri
 *   /topic/alerts    - Alarm bildirimleri
 *   /topic/devices   - Cihaz durum degisiklikleri
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Istemcilerin abone olacagi prefix
        config.enableSimpleBroker("/topic");
        // Istemcilerden gelecek mesajlarin prefix'i
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket endpoint - SockJS fallback ile
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
