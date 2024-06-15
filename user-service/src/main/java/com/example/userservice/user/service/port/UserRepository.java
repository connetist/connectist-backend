package com.example.userservice.user.service.port;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserUpdate;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    User save(User user);

    User update(User user);

    /**
     * TODO
     * delete(String id)
     * update(User user)
     */
}
