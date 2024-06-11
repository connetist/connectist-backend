package com.example.userservice.user.infrastructure.join;

import com.example.userservice.user.domain.join.JoinUser;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JoinUserConcurrentMapRepository implements JoinUserRepository {

    private static Map<String, JoinUser> mapRepository = new ConcurrentHashMap<>();

    @Override
    public JoinUser save(JoinUser joinUser) {
        return mapRepository.put(joinUser.getEmail(), joinUser);
    }

    @Override
    public boolean obtain(String email) {
        return mapRepository.containsKey(email);
    }

    @Override
    public JoinUser findByEmail(String email) {
        return mapRepository.get(email);
    }
}
