package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class PessimisticOrderService implements StockManager {
    private final StockService stockService;

    public PessimisticOrderService(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public void reserve(OrderRequest request) {
        stockService.reserveWithPessimisticLock(request.getProductId(), request.getQuantity());
    }

    @Override
    public void decrease(OrderRequest request) {
        stockService.decreaseWithPessimisticLock(request.getProductId(), request.getQuantity());
    }

    @Override
    public void rollback(OrderRequest request) {
    }
}
