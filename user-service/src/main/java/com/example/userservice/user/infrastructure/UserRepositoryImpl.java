package com.example.userservice.user.infrastructure;

import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.user.domain.User;
import com.example.userservice.util.exception.code.GlobalException;
import com.example.userservice.user.infrastructure.entity.UserEntity;
import com.example.userservice.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJPARepository;

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return userJPARepository.findByEmail(email).map(UserEntity::toModel);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try {
            return userJPARepository.findById(id).map(UserEntity::toModel);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // error?
    @Override
    public User save(User user) {
        try {
            return userJPARepository.save(UserEntity.from(user)).toModel();
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User update(User user) {
        try {
            return userJPARepository.save(UserEntity.from(user)).toModel();
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User delete(User user) {
        try {
            userJPARepository.deleteById(UserEntity.from(user).getId());
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return user;
    }
}
