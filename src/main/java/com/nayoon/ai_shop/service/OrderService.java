package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.domain.model.OrderRepository;
import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.Order;
import com.nayoon.ai_shop.exception.PaymentException;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final PaymentService paymentService;
    private final StockService stockService;
    private final RedisStockService redisStockService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    public OrderService(PaymentService paymentService, StockService stockService, RedisStockService redisStockService, ProductService productService, OrderRepository orderRepository) {
        this.paymentService = paymentService;
        this.stockService = stockService;
        this.redisStockService = redisStockService;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    public Long order(OrderRequest orderRequest) {
        try {
            // 재고 선점
            boolean reserved = redisStockService.reserve(orderRequest.getProductId(), orderRequest.getQuantity());
            if (!reserved) throw new IllegalStateException("재고 부족");

            Product product = productService.getProduct(orderRequest.getProductId());

            // 결제 시도
            boolean paid = paymentService.pay(product.getPrice());
            if (!paid) throw new PaymentException("결제 실패");

            // 결제 성공 → DB 반영
            stockService.decrease(orderRequest.getProductId(), orderRequest.getQuantity()); // 실제 재고 감소
            return orderRepository.save(Order.from(orderRequest, product)).getOrderId(); // 주문 기록
        } catch (RuntimeException e) {
            // 결제 실패 or 에러 → Redis 재고 복구
            redisStockService.rollback(orderRequest.getProductId(), orderRequest.getQuantity());
            throw e;
        }
    }
}
