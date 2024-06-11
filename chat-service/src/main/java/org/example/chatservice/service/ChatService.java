package org.example.chatservice.service;

import org.example.chatservice.domain.ChatRoom;
import org.example.chatservice.infrastructure.entity.ChatRoomEntity;
import org.example.chatservice.infrastructure.repository.ChatRepository;

import java.lang.reflect.Member;

public interface ChatService {

    ChatRoom saveChatRoom(ChatRoom chatRoom);
//    void addMember(Member member);
}
