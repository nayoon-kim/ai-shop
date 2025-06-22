package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.exception.SoldOutException;
import jakarta.transaction.Transactional;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisStockService {
    private final RedisService redisService;
    private final RedissonClient redissonClient;

    public RedisStockService(RedisService redisService, RedissonClient redissonClient) {
        this.redisService = redisService;
        this.redissonClient = redissonClient;
    }

    @Transactional
    public boolean reserve(Long productId, int quantity) throws InterruptedException {
        RLock rlock = redissonClient.getLock("lock:product:1");
        if (rlock.tryLock(3, 1, TimeUnit.SECONDS)) {
            try {
                Long stock = redisService.decrement("stock:product:1");
                if (stock <= 0) {
                    throw new SoldOutException();
                }
                
                return true;
            } catch (SoldOutException e) {
                throw new RuntimeException(e);
            } finally {
                rlock.unlock();
            }
        }
        return false;
    }

    public void rollback(Long productId, int quantity) {
    }
}
