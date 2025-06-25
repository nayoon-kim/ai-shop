package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.domain.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long productId;
    private Long quantity;
    private int price;
    private PaymentMethod method;
    private String cardToken;

    public Order(Long productId, Long quantity, int price, PaymentMethod method, String cardToken) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.method = method;
        this.cardToken = cardToken;
    }

    public static Order from(OrderRequest orderRequest, Product product) {
        return new Order(orderRequest.getProductId(), orderRequest.getQuantity(), product.getPrice(),
                orderRequest.getPaymentRequest().getMethod(), orderRequest.getPaymentRequest().getCardToken());
    }
}
