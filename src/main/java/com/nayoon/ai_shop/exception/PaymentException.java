package com.nayoon.ai_shop.exception;

public class PaymentException extends Exception{
    protected String msg;

    public PaymentException(String msg) {
        this.msg = msg;
    }
}
