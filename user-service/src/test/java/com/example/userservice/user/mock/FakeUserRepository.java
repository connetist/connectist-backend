package com.example.userservice.user.mock;

import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.infrastructure.entity.UserEntity;
import com.example.userservice.user.infrastructure.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {

    private final Map<String, UserEntity> userMap = new HashMap<>();

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userMap.get(email)).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findById(String id) {
        for (UserEntity user : userMap.values()) {
            if (user.getId().equals(id)) {
                return Optional.of(user.toModel());
            }
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        userMap.put(UserEntity.from(user).getEmail(), UserEntity.from(user));
        return user;
    }

    @Override
    public User update(User user) {
        userMap.remove(user.getEmail());
        userMap.put(user.getEmail(), UserEntity.from(user));
        return userMap.get(user.getEmail()).toModel();
    }

    @Override
    public User delete(User user) {
        return userMap.remove(UserEntity.from(user).getEmail()).toModel();
    }
}
