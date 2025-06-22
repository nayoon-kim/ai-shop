package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.exception.LockInterruptedException;
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
    public boolean reserve(Long productId, Long quantity) {
        RLock rlock = redissonClient.getLock("lock:product:" + productId);
        try {
            if (rlock.tryLock(3, 1, TimeUnit.SECONDS)) {
                Long stock = redisService.decrement("stock:product:" + productId, quantity);
                if (stock <= 0) {
                    throw new SoldOutException();
                }
                return true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockInterruptedException("Lock interrupted", e);
        } finally {
            rlock.unlock();
        }
        return false;
    }

    public void rollback(Long productId, Long quantity) {
        RLock rlock = redissonClient.getLock("lock:product:" + productId);
        try {
            if (rlock.tryLock(3, 1, TimeUnit.SECONDS)) {
                Long stock = redisService.increment("stock:product:" + productId, quantity);
                if (stock <= 0) {
                    throw new SoldOutException();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockInterruptedException("Lock interrupted", e);
        } finally {
            rlock.unlock();
        }
    }
}
