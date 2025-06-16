package com.nayoon.ai_shop.domain.model;

import java.time.LocalDateTime;

public class Brand {
    private final Long id;
    private final String name;
    private final LocalDateTime registeredAt;

    public Brand(Long id, String name, LocalDateTime registeredAt) {
        this.id = id;
        this.name = name;
        this.registeredAt = registeredAt;
    }
}
