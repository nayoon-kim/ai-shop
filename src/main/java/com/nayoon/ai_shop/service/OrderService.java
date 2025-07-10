package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.domain.model.OrderRepository;
import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.Order;
import com.nayoon.ai_shop.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final PaymentService paymentService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final RollbackRetryProducer rollbackRetryProducer;
    private final StockManager stockManager;

    public OrderService(PaymentService paymentService, ProductService productService, OrderRepository orderRepository,
                        RollbackRetryProducer rollbackRetryProducer, StockManager stockManager
    ) {
        this.paymentService = paymentService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.rollbackRetryProducer = rollbackRetryProducer;
        this.stockManager = stockManager;
    }

    @Transactional
    public Long order(OrderRequest orderRequest) {
        Product product = productService.getProduct(orderRequest.getProductId());
        Order order = Order.from(orderRequest, product);
        try {
            // 재고 선점
            stockManager.reserve(orderRequest);

            // 결제 시도
            boolean paid = paymentService.pay(product.getPrice());
            if (!paid) throw new PaymentException("결제 실패"); // todo catch

            order.markAsPaid();

            // 결제 성공 → DB 반영
            stockManager.decrease(orderRequest); // 실제 재고 감소
        } catch (StockException e) {
            order.markAsFailed();                // 실패 상태로 마킹
            orderRepository.save(order);        // 실패 주문도 저장

            try {
                // 결제 실패 or 에러 → Redis 재고 복구
                stockManager.rollback(orderRequest);     // 먼저 Redis 재고 복구
            } catch (RuntimeException ex) {
                order.markAsRollbackFailed();
                orderRepository.save(order);

                // 2. 비동기 보상 처리용 Kafka 메시지 발행
                rollbackRetryProducer.send(orderRequest.getId(), orderRequest.getProductId(), orderRequest.getQuantity());
            }

            throw e;
        }

        return orderRepository.save(order).getOrderId(); // 주문 기록
    }
}
