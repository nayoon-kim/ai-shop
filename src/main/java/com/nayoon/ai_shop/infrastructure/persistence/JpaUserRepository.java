package com.nayoon.ai_shop.infrastructure.persistence;

import com.nayoon.ai_shop.domain.model.User;
import com.nayoon.ai_shop.domain.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class JpaUserRepository implements UserRepository {
    @Autowired
    private SpringDataUserRepository jpaRepository;

    public JpaUserRepository() {}

    public JpaUserRepository(SpringDataUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(entity -> new User(entity.getId(), entity.getEmail(), entity.getPasswordHash()));
    }

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity(user.getEmail(), user.getPasswordHash());
        jpaRepository.save(entity);
    }
}
