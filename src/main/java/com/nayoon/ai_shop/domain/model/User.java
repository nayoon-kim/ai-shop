package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.infrastructure.security.PasswordHasher;

public class User {
    private final Long id;
    private final String email;
    private final String passwordHash;

    public User(Long id, String email, String passwordHash) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public boolean isPasswordMatch(String rawPassword, PasswordHasher hasher) {
        return hasher.matches(rawPassword, passwordHash);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
