package com.example.userservice.user.controller.port;

import com.example.userservice.user.domain.join.JoinUserCreate;
import com.example.userservice.user.domain.join.JoinUser;

public interface JoinUserService {
    JoinUser join(JoinUserCreate UserCreate);
}
