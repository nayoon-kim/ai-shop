package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.OrderRepository;
import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.exception.PaymentException;
import com.nayoon.ai_shop.exception.SoldOutException;
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

    public void order(Long productId, int quantity) throws InterruptedException, PaymentException, SoldOutException {
        boolean reserved = redisStockService.reserve(productId, quantity);
        if (!reserved) throw new IllegalStateException("재고 부족");

        Product product = productService.getProduct(productId);
        try {
            boolean paid = paymentService.pay(product.getPrice());
            if (!paid) throw new PaymentException("결제 실패");

            // 결제 성공 → DB 반영
            stockService.decrease(productId, quantity); // 실제 재고 감소
//            orderRepository.save(Order.createFrom(paymentRequest)); // 주문 기록
        } catch (Exception e) {
            // 결제 실패 or 에러 → Redis 재고 복구
            redisStockService.rollback(productId, quantity);
            throw e;
        }
    }
}
