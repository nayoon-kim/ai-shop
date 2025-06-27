package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.domain.model.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class PessimisticOrderService extends OrderService {
    public PessimisticOrderService(PaymentService paymentService, StockService stockService,
                                   RedisStockService redisStockService, ProductService productService,
                                   OrderRepository orderRepository) {
        super(paymentService, stockService, redisStockService, productService, orderRepository);
    }

    @Override
    protected boolean reserve(OrderRequest request) {
        return stockService.reserveWithPessimisticLock(request.getProductId(), request.getQuantity());
    }

    @Override
    protected void decrease(OrderRequest request) {
        stockService.decrease(request.getProductId(), request.getQuantity());
    }

    @Override
    protected void rollback(OrderRequest request) {

    }
}
