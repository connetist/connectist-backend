package com.example.userservice.user.controller.port;

import com.example.userservice.user.controller.request.UserLogin;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.create.UserCreate;
import com.example.userservice.user.domain.token.UserWithToken;

public interface UserService {

    // 회원가입
    User create(UserCreate userCreate);

    // 로그인
    UserWithToken login(UserLogin userLogin);

    User update(User user);

    User delete(String id);

    // Read
    User findByEmail(String email);

    User findById(String id);
}
