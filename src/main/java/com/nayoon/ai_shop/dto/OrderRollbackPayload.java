package com.nayoon.ai_shop.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderRollbackPayload {
    private Long orderId;
    private Long productId;
    private Long quantity;
}
