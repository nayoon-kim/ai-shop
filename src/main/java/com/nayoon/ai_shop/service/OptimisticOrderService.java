package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class OptimisticOrderService implements StockManager {
    private final StockService stockService;

    public OptimisticOrderService(StockService stockService1) {
        this.stockService = stockService1;
    }

    @Override
    public void reserve(OrderRequest request) {
        stockService.reserveWithOptimisticLock(request.getProductId(), request.getQuantity());
    }

    @Override
    public void decrease(OrderRequest request)  {
    }

    @Override
    public void rollback(OrderRequest request) {
    }
}
