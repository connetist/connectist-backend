package com.example.userservice.user.infrastructure.join;

import com.example.userservice.user.domain.join.JoinUser;

public interface JoinUserRepository {
    JoinUser findByEmail(String email);

    JoinUser save(JoinUser joinUser);

    boolean obtain(String email);

    JoinUser replace(String email, JoinUser newJoinUser);

    JoinUser delete(String email);
}
