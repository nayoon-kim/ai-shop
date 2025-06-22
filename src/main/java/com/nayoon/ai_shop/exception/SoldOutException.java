package com.nayoon.ai_shop.exception;

public class SoldOutException extends RuntimeException {
    public SoldOutException() {
        super();
    }

    public SoldOutException(String msg) {
        super(msg);
    }
}
