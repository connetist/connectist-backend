package com.example.userservice.user.infrastructure.join;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.util.exception.code.GlobalException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JoinUserConcurrentMapRepository implements JoinUserRepository {

    private static final Map<String, JoinUser> mapRepository = new ConcurrentHashMap<>();

    @Override
    public Optional<JoinUser> delete(String email) {
        try {
            return Optional.ofNullable(mapRepository.remove(email));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public JoinUser save(JoinUser joinUser) {
        try {
            return mapRepository.put(joinUser.getEmail(), joinUser);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<JoinUser> replace(String email, JoinUser newJoinUser) {
        try {
            return Optional.ofNullable(mapRepository.replace(email, newJoinUser));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public boolean obtain(String email) {
        try {
            return mapRepository.containsKey(email);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<JoinUser> findByEmail(String email) {
        try {
            return Optional.ofNullable(mapRepository.get(email));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
