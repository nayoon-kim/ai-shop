package com.nayoon.ai_shop.exception;

public class LockInterruptedException extends StockException {
    public LockInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }
}
