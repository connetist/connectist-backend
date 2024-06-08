package org.example.chatservice.infrastructure.repository;

import org.example.chatservice.domain.ChatRoom;
import org.example.chatservice.infrastructure.entity.ChatRoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends MongoRepository<ChatRoomEntity,String> {
}
