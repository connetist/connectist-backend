package com.example.userservice.user.mock;

import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.user.infrastructure.user.join.JoinUserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeUserDigitRepository implements JoinUserRepository {

    private final Map<String, UserJoin> joinUserMap = new ConcurrentHashMap<>();

    @Override
    public Optional<UserJoin> findByEmail(String email) {
        return Optional.ofNullable(joinUserMap.get(email));
    }

    @Override
    public UserJoin save(UserJoin userJoin) {
        return joinUserMap.put(userJoin.getEmail(), userJoin);
    }

    @Override
    public boolean obtain(String email) {
        return joinUserMap.containsKey(email);
    }

    @Override
    public Optional<UserJoin> replace(String email, UserJoin newUserJoin) {
        return Optional.ofNullable(joinUserMap.replace(email, newUserJoin));
    }

    @Override
    public Optional<UserJoin> delete(String email) {
        UserJoin remove = joinUserMap.remove(email);
        return Optional.ofNullable(remove);
    }
}
