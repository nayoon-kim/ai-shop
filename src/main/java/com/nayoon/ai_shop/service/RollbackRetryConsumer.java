package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.dto.OrderRollbackPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RollbackRetryConsumer {
    private final RedisStockService redisStockService;

    @KafkaListener(topics = "${kafka.topic.rollback}", groupId = "rollback-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderRollbackPayload payload) {
        log.info("Received rollback request: {}", payload);

        // 실제 롤백 처리 로직
        redisStockService.rollback(payload.getProductId(), payload.getQuantity());
    }
}
