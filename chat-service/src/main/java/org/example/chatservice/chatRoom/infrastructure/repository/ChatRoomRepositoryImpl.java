package org.example.chatservice.chatRoom.infrastructure.repository;
import lombok.RequiredArgsConstructor;
import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom{
    private final MongoTemplate mongoTemplate;
}
