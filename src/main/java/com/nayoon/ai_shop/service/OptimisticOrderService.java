package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.domain.model.OrderRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class OptimisticOrderService extends OrderService {

    public OptimisticOrderService(PaymentService paymentService, StockService stockService,
                                  RedisStockService redisStockService, ProductService productService,
                                  OrderRepository orderRepository) {
        super(paymentService, stockService, redisStockService, productService, orderRepository);
    }

    @Override
    protected void reserve(OrderRequest request) {
        stockService.reserveWithOptimisticLock(request.getProductId(), request.getQuantity());
    }

    @Override
    protected void decrease(OrderRequest request)  {
    }

    @Override
    protected void rollback(OrderRequest request) {
        stockService.rollbackWithOptimisticLock(request.getProductId(), request.getQuantity());
        paymentService.cancel(request);
    }
}
