package com.example.userservice.user.controller.port;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserCreate;

public interface UserService {
    User create(UserCreate userCreate);

    User update(User user);

    User delete(String id);

    // Read
    User findByEmail(String email);

    User findById(String id);
}
