package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.exception.LockAcquisitionException;
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
    public void reserve(Long productId, Long quantity) {
        RLock rlock = redissonClient.getLock("lock:product:" + productId);
        boolean locked = false;
        try {
            locked = rlock.tryLock(3, 1, TimeUnit.SECONDS);
            if (locked) {
                Long stock = redisService.decrement("stock:product:" + productId, quantity);
                if (stock < 0) {
                    throw new SoldOutException();
                }
            } else {
                throw new LockAcquisitionException("락 획득 실패");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockInterruptedException("Lock interrupted", e);
        } finally {
            if (locked) {
                rlock.unlock();
            }
        }
    }

    @Transactional
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
