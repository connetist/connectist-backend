package com.example.userservice.user.controller.port;

import com.example.userservice.user.domain.UserCreate;
import com.example.userservice.user.domain.join.JoinUser;

public interface JoinUserService {
    JoinUser create(UserCreate UserCreate);
}
