package org.example.chatservice.chatRoom.infrastructure.repository;

import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



public interface ChatRoomRepository extends MongoRepository<ChatRoomEntity,String>,ChatRoomRepositoryCustom {

}
