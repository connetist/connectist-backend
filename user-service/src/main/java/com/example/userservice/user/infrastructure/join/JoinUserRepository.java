package com.example.userservice.user.infrastructure.join;

import com.example.userservice.user.domain.join.JoinUser;

import java.util.Optional;

public interface JoinUserRepository {
    Optional<JoinUser> findByEmail(String email);

    JoinUser save(JoinUser joinUser);

    boolean obtain(String email);

    Optional<JoinUser> replace(String email, JoinUser newJoinUser);

    Optional<JoinUser> delete(String email);
}
