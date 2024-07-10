package com.example.userservice.user.infrastructure.user.join;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.domain.user.UserJoin;
import com.example.userservice.util.exception.code.GlobalException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JoinUserConcurrentMapRepository implements JoinUserRepository {

    private static final Map<String, UserJoin> mapRepository = new ConcurrentHashMap<>();

    @Override
    public Optional<UserJoin> delete(String email) {
        try {
            return Optional.ofNullable(mapRepository.remove(email));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public UserJoin save(UserJoin userJoin) {
        try {
            return mapRepository.put(userJoin.getEmail(), userJoin);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<UserJoin> replace(String email, UserJoin newUserJoin) {
        try {
            return Optional.ofNullable(mapRepository.replace(email, newUserJoin));
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
    public Optional<UserJoin> findByEmail(String email) {
        try {
            return Optional.ofNullable(mapRepository.get(email));
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
