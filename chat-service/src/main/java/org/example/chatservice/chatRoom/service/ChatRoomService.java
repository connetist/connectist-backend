package org.example.chatservice.chatRoom.service;

import org.example.chatservice.chatRoom.domain.ChatRoom;
import org.example.chatservice.chatRoom.dto.CreateChatRoomRequest;
import org.example.chatservice.chatRoom.dto.UpdateChatRoomRequest;
import org.example.chatservice.utils.UuidHolder;

import java.util.List;

public interface ChatRoomService {

    // 채팅방
    ChatRoom createChatRoom(CreateChatRoomRequest rq);
    void deleteChatRoom(String chatRoomId);
    ChatRoom updateChatRoom(UpdateChatRoomRequest rq);

    void deleteMember(String chatRoomId, String memberId);

    List<ChatRoom> getAllChatRooms();
    ChatRoom getChatRoom(String chatRoomId);



}
