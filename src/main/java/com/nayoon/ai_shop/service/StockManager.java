package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;

public interface StockManager {
    public void reserve(OrderRequest request);
    public void decrease(OrderRequest request);
    public void rollback(OrderRequest request);
}
