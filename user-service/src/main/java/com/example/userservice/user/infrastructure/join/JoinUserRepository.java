package com.example.userservice.user.infrastructure.join;

import com.example.userservice.user.domain.join.JoinUser;

public interface JoinUserRepository {
    JoinUser findByEmail(String email);

    JoinUser save(JoinUser joinUser);
}