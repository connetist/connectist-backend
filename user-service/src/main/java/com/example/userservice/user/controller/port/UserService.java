package com.example.userservice.user.controller.port;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.UserCreate;

public interface UserService {

    // 회원가입
    User create(UserCreate userCreate);

    User update(User user);

    User delete(String id);

    // Read
    User findByEmail(String email);

    User findById(String id);
}
