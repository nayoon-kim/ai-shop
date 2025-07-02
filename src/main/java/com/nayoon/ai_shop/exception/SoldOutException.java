package com.nayoon.ai_shop.exception;

public class SoldOutException extends StockException {
    public SoldOutException() {
        super();
    }

    public SoldOutException(String message) {
        super(message);
    }
}
