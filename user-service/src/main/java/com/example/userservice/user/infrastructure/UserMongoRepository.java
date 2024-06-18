package com.example.userservice.user.infrastructure;

import com.example.userservice.user.infrastructure.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByid(String id);

    UserEntity save(UserEntity userEntity);

    UserEntity deleteUserEntityBy(UserEntity userEntity);

    void deleteById(String id);
}
