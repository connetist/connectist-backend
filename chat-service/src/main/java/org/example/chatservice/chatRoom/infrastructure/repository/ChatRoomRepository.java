package org.example.chatservice.chatRoom.infrastructure.repository;

import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.infrastructure.entity.ChatRoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface ChatRoomRepository  {

    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findById(String id);
    Optional<List<ChatRoom>> findAll();

    void deleteById(String id);


}
