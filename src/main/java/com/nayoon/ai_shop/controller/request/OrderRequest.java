package com.nayoon.ai_shop.controller.request;

import lombok.Getter;

@Getter
public class OrderRequest {
    private Long id;
    private Long productId;
    private Long quantity;
    private PaymentRequest paymentRequest;
}
