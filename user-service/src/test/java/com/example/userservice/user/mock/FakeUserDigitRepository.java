package com.example.userservice.user.mock;

import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.infrastructure.join.JoinUserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeUserDigitRepository implements JoinUserRepository {

    private final Map<String, JoinUser> joinUserMap = new ConcurrentHashMap<>();

    @Override
    public Optional<JoinUser> findByEmail(String email) {
        return Optional.ofNullable(joinUserMap.get(email));
    }

    @Override
    public JoinUser save(JoinUser joinUser) {
        return joinUserMap.put(joinUser.getEmail(), joinUser);
    }

    @Override
    public boolean obtain(String email) {
        return joinUserMap.containsKey(email);
    }

    @Override
    public Optional<JoinUser> replace(String email, JoinUser newJoinUser) {
        return Optional.ofNullable(joinUserMap.replace(email, newJoinUser));
    }

    @Override
    public Optional<JoinUser> delete(String email) {
        JoinUser remove = joinUserMap.remove(email);
        return Optional.ofNullable(remove);
    }
}
