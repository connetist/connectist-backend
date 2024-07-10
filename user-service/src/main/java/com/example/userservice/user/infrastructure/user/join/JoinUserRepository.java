package com.example.userservice.user.infrastructure.user.join;

import com.example.userservice.user.domain.user.UserJoin;

import java.util.Optional;

public interface JoinUserRepository {
    Optional<UserJoin> findByEmail(String email);

    UserJoin save(UserJoin userJoin);

    boolean obtain(String email);

    Optional<UserJoin> replace(String email, UserJoin newUserJoin);

    Optional<UserJoin> delete(String email);
}
