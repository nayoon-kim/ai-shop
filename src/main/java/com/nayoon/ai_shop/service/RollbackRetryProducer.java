package com.nayoon.ai_shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollbackRetryProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "rollback"; // todo  properties -> yml

    public void send(Long orderId, Long productId, Long quantity) {
        String payload = buildPayload(orderId, productId, quantity);
        kafkaTemplate.send(TOPIC, payload); // todo check dto
    }

    private String buildPayload(Long orderId, Long productId, Long quantity) {
        return String.format("{\"orderId\":%d,\"productId\":%d,\"quantity\":%d}", orderId, productId, quantity);
    }
}
