package org.example.chatservice.chatRoom.infrastructure.repository;

import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatMemberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatMemberMongoRepository extends MongoRepository<ChatMemberEntity, String> {

    Optional<ChatMemberEntity> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
