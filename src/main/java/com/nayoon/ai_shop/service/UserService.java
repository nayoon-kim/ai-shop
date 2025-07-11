package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.User;
import com.nayoon.ai_shop.infrastructure.security.JwtTokenProvider;
import com.nayoon.ai_shop.infrastructure.security.PasswordHasher;
import com.nayoon.ai_shop.domain.model.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHasher hasher;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordHasher hasher, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.hasher = hasher;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void register(String email, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }
        String hash = hasher.hash(rawPassword);
        User user = new User(email, hash);
        userRepository.save(user);
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isPasswordMatch(rawPassword, hasher)) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtTokenProvider.createToken(user.getId(), user.getEmail());
    }
}
