package com.nayoon.ai_shop.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long id;
    private Long productId;
    private Long quantity;
    private PaymentRequest paymentRequest;
}
