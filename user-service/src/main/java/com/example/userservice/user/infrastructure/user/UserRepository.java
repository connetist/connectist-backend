package com.example.userservice.user.infrastructure.user;

import com.example.userservice.user.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    User save(User user);

    User update(User user);

    User delete(User user);
}
