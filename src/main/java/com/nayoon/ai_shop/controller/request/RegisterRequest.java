package com.nayoon.ai_shop.controller.request;

public class RegisterRequest {
    private String email;
    private String password;

    // 기본 생성자 (JSON 역직렬화용)
    public RegisterRequest() {}

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
