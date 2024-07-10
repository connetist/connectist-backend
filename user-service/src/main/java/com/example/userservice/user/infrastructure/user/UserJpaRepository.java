package com.example.userservice.user.infrastructure.user;

import com.example.userservice.user.infrastructure.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

    @NotNull
    Optional<UserEntity> findById(String id);

    UserEntity save(@NotNull UserEntity userEntity);

    void deleteById(String id);
}
