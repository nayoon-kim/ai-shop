package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.domain.model.enums.OrderStatus;
import com.nayoon.ai_shop.domain.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Column(nullable = false)
    private String cardToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public Order(Long productId, Long quantity, int price, PaymentMethod method, String cardToken, OrderStatus status) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.method = method;
        this.cardToken = cardToken;
        this.status = status;
    }

    public void markAsFailed() {
        this.status = OrderStatus.FAILED;
    }

    public void markAsPaid() {
        this.status = OrderStatus.PAID;
    }

    public static Order from(OrderRequest orderRequest, Product product) {
        return new Order(orderRequest.getProductId(), orderRequest.getQuantity(),
                product.getPrice(), orderRequest.getPaymentRequest().getMethod(),
                orderRequest.getPaymentRequest().getCardToken(), OrderStatus.PENDING);
    }
}
