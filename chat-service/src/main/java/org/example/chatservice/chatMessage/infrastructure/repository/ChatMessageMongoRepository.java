package org.example.chatservice.chatMessage.infrastructure.repository;

import org.example.chatservice.chatMessage.infrastructure.entity.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageMongoRepository extends MongoRepository<ChatMessageEntity,String> {
    List<ChatMessageEntity> findByRoomId(String roomId);
}
