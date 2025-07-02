package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.domain.model.OrderRepository;
import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.Order;
import com.nayoon.ai_shop.exception.*;
import org.springframework.transaction.annotation.Transactional;

public abstract class OrderService {
    protected final PaymentService paymentService;
    protected final StockService stockService;
    protected final RedisStockService redisStockService;
    protected final ProductService productService;
    protected final OrderRepository orderRepository;

    public OrderService(PaymentService paymentService, StockService stockService, RedisStockService redisStockService, ProductService productService, OrderRepository orderRepository) {
        this.paymentService = paymentService;
        this.stockService = stockService;
        this.redisStockService = redisStockService;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Long order(OrderRequest orderRequest) {
        Product product = productService.getProduct(orderRequest.getProductId());
        Order order = Order.from(orderRequest, product);
        try {
            // 재고 선점
            reserve(orderRequest);

            // 결제 시도
            boolean paid = paymentService.pay(product.getPrice());
            if (!paid) throw new PaymentException("결제 실패");

            order.markAsPaid();

            // 결제 성공 → DB 반영
            decrease(orderRequest); // 실제 재고 감소
        } catch (StockException e) {
            // 결제 실패 or 에러 → Redis 재고 복구
            rollback(orderRequest);     // 먼저 Redis 재고 복구
            order.markAsFailed();                // 실패 상태로 마킹
            orderRepository.save(order);        // 실패 주문도 저장

            throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return orderRepository.save(order).getOrderId(); // 주문 기록
    }

    protected abstract void reserve(OrderRequest request);
    protected abstract void decrease(OrderRequest request) throws InterruptedException;
    protected abstract void rollback(OrderRequest request);
}
