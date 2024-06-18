package org.example.chatservice.chatMessage.infrastructure.repository;

import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.infrastructure.entity.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository  {
    ChatMessage save(ChatMessage chatMessage);

    Optional<List<ChatMessage>> findAll();

}
