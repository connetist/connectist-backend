package org.example.chatservice.chatMessage.infrastructure.repository;

import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.infrastructure.entity.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {

}
