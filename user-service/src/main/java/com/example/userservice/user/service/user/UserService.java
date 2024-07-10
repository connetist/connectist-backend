package com.example.userservice.user.service.user;

import com.example.userservice.user.dto.request.user.UserCreateRequest;
import com.example.userservice.user.dto.request.user.UserDeleteRequest;
import com.example.userservice.user.dto.request.user.UserLoginRequest;
import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.dto.request.user.UserUpdateRequest;
import com.example.userservice.user.dto.response.token.UserWithToken;

public interface UserService {

    // 회원가입
    User create(UserCreateRequest userCreateRequest);

    UserWithToken login(UserLoginRequest userLogin);

    User update(UserUpdateRequest userUpdateRequest);

    User delete(UserDeleteRequest userDeleteRequest);

    User findByEmail(String email);

    User findById(String id);
}
