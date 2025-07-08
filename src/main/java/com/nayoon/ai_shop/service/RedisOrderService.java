package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class RedisOrderService implements StockManager {
    private final RedisStockService redisStockService;
    private final StockService stockService;

    public RedisOrderService(RedisStockService redisStockService1, StockService stockService1) {
        this.redisStockService = redisStockService1;
        this.stockService = stockService1;
    }

    @Override
    public void reserve(OrderRequest request) {
        redisStockService.reserve(request.getProductId(), request.getQuantity());
    }

    @Override
    public void decrease(OrderRequest request) {
        stockService.decrease(request.getProductId(), request.getQuantity());
    }

    @Override
    public void rollback(OrderRequest request) {
        redisStockService.rollback(request.getProductId(), request.getQuantity());
    }
}
