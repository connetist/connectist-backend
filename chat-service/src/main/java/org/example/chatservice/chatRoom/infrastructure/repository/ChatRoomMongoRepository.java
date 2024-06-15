package org.example.chatservice.chatRoom.infrastructure.repository;

import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomMongoRepository extends MongoRepository<ChatRoomEntity,String> {

}
