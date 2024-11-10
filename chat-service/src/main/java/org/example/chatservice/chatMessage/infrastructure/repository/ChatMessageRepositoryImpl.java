package org.example.chatservice.chatMessage.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.example.chatservice.chatMessage.infrastructure.entity.ChatMessageEntity;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomMongoRepository;
import org.example.chatservice.chatRoom.infrastructure.repository.ChatRoomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageMongoRepository chatMessageMongoRepository;
    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageMongoRepository.save(ChatMessageEntity.from(chatMessage)).toModel();
    }

    @Override
    public Optional<List<ChatMessage>> findAllByRoomId(String roomId) {
        List<ChatMessage> chatMessages = chatMessageMongoRepository.findByRoomId(roomId)
                .stream()
                .map(ChatMessageEntity::toModel)
                .toList();
        return Optional.of(chatMessages);

    }

    @Override
    public void deleteAll() {
        chatMessageMongoRepository.deleteAll();
    }

    @Override
    public void saveAll(List<ChatMessage> messages) {
        List<ChatMessageEntity> entities = messages.stream()
                .map(ChatMessageEntity::from)
                .collect(Collectors.toList());

        // 변환된 엔티티를 리포지토리에 저장
        chatMessageMongoRepository.saveAll(entities);
    }


    @Override
    public Optional<List<ChatMessage>> findByRoomIdAndCreatedAtBeforeOrderByCreatedAtDesc(String roomId, long createdAt, Pageable pageable) {
        List<ChatMessageEntity> entities = chatMessageMongoRepository.findByRoomIdAndCreatedAtBeforeOrderByCreatedAtDesc(roomId, createdAt, pageable);
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        List<ChatMessage> chatMessages = entities.stream()
                .map(ChatMessageEntity::toModel)
                .collect(Collectors.toList());
        return Optional.of(chatMessages);
    }

    @Override
    public Optional<List<ChatMessage>> findByRoomIdOrderByCreatedAtDesc(String roomId, Pageable pageable) {
        List<ChatMessageEntity> entities = chatMessageMongoRepository.findByRoomIdOrderByCreatedAtDesc(roomId, pageable);
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        List<ChatMessage> chatMessages = entities.stream()
                .map(ChatMessageEntity::toModel)
                .collect(Collectors.toList());
        return Optional.of(chatMessages);
    }
}
