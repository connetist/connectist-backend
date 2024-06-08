package org.example.chatservice.service;

import org.example.chatservice.domain.ChatRoom;
import org.example.chatservice.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.infrastructure.repository.ChatRepository;

public interface ChatService {

    ChatRoom saveMessage(ChatRoom chatRoom);
}
