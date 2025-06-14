package com.nayoon.ai_shop.domain.model;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
}
