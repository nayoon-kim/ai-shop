package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.dto.OrderRollbackPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollbackRetryProducer {
    private final KafkaTemplate<String, OrderRollbackPayload> kafkaTemplate;
    @Value("${kafka.topic.rollback}")
    private String rollbackTopic;

    public void send(Long orderId, Long productId, Long quantity) {
        kafkaTemplate.send(rollbackTopic, buildPayload(orderId, productId, quantity));
    }

    private OrderRollbackPayload buildPayload(Long orderId, Long productId, Long quantity) {
        return new OrderRollbackPayload(orderId, productId, quantity);
    }
}
