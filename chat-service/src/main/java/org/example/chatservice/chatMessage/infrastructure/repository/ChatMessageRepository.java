package org.example.chatservice.chatMessage.infrastructure.repository;

import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.infrastructure.entity.ChatMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository  {
    ChatMessage save(ChatMessage chatMessage);

    Optional<List<ChatMessage>> findAllByRoomId(String roomId);

    void deleteAll();

    void saveAll(List<ChatMessage> messages);

    // 커서 기반 페이징을 위한 메서드 추가
    Optional<List<ChatMessage>> findByRoomIdAndCreatedAtBeforeOrderByCreatedAtDesc(String roomId, long createdAt, Pageable pageable);

    Optional<List<ChatMessage>> findByRoomIdOrderByCreatedAtDesc(String roomId, Pageable pageable);
}
