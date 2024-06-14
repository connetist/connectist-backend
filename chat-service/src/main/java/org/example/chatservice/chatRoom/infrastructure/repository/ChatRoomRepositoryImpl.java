package org.example.chatservice.chatRoom.infrastructure.repository;
import lombok.RequiredArgsConstructor;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomMongoRepository chatRoomMongoRepository;
    @Override
    public ChatRoom save(ChatRoom chatRoom) {

        return chatRoomMongoRepository.save(ChatRoomEntity.from(chatRoom)).toModel();
    }

    @Override
    public Optional<ChatRoom> findById(String id) {
        return chatRoomMongoRepository.findById(id).map(ChatRoomEntity::toModel);
    }

    @Override
    public Optional<List<ChatRoom>> findAll() {
        List<ChatRoom> chatRooms = chatRoomMongoRepository.findAll()
                .stream()
                .map(ChatRoomEntity::toModel)
                .collect(Collectors.toList());
        return Optional.of(chatRooms);
    }

    @Override
    public void deleteById(String id) {
        chatRoomMongoRepository.deleteById(id);

    }
}
