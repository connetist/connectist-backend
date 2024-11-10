package org.example.chatservice.chatRoom.service;

import org.example.chatservice.chatRoom.domain.ChatMember;
import org.example.chatservice.chatRoom.domain.ChatRoomMembership;

import java.util.List;

public interface ChatMembershipService {
    void joinChatRoom(String userId, String roomId);
    void leaveChatRoom(String userId, String roomId);
    ChatMember createChatMember(String userId);
    List<ChatRoomMembership> getChatRooms(String userId);
    boolean isUserExists(String userId);
}
