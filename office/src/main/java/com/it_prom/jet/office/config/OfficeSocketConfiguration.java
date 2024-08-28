package com.it_prom.jet.office.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.it_prom.jet.common.procossor.MessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class OfficeSocketConfiguration implements WebSocketConfigurer {

    private final MessageConverter messageConverter;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Cache<String, WebSocketSession> sessionCache;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new OfficeSocketHandler(messageConverter, kafkaTemplate, sessionCache))
                .setAllowedOrigins("*");

    }
}
