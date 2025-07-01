package com.nayoon.ai_shop.exception;

public class StockException extends RuntimeException {
    public StockException() { super(); }

    public StockException(String message) {
        super(message);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}
