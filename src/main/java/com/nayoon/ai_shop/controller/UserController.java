package com.nayoon.ai_shop.controller;

import com.nayoon.ai_shop.controller.request.LoginRequest;
import com.nayoon.ai_shop.controller.request.RegisterRequest;
import com.nayoon.ai_shop.controller.response.TokenResponse;
import com.nayoon.ai_shop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("registered: " + request.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}