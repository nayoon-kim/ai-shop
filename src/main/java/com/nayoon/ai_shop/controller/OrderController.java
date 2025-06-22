package com.nayoon.ai_shop.controller;

import com.nayoon.ai_shop.controller.request.StockRequest;
import com.nayoon.ai_shop.exception.PaymentException;
import com.nayoon.ai_shop.exception.SoldOutException;
import com.nayoon.ai_shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/stocks")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/buy")
    private ResponseEntity<String> order(@RequestBody StockRequest stockRequest) throws InterruptedException, PaymentException, SoldOutException {
        orderService.order(stockRequest.getProductId(), stockRequest.getQuantity());
        return ResponseEntity.ok("registered: ");
    }
}
