package com.example.userservice.user.infrastructure;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.service.port.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRespository {

    private final UserMongoRepository userMongoRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userMongoRepository.findByEmail(email).map(UserEntity::toModel);

    }

    @Override
    public Optional<User> findById(String id) {
        return userMongoRepository.findByid(id).map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return userMongoRepository.save(UserEntity.from(user)).toModel();
    }

    /**
     * TODO
     * update(
     */
}
