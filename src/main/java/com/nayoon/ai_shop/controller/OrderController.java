package com.nayoon.ai_shop.controller;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import com.nayoon.ai_shop.controller.response.OrderResponse;
import com.nayoon.ai_shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    private ResponseEntity<OrderResponse> order(@RequestBody OrderRequest orderRequest) {
        Long orderId = orderService.order(orderRequest);
        return ResponseEntity.ok(new OrderResponse(orderId));
    }
}
