package com.nayoon.ai_shop.config;

import com.nayoon.ai_shop.infrastructure.persistence.JpaUserRepository;
import com.nayoon.ai_shop.domain.model.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository();
    }
}
