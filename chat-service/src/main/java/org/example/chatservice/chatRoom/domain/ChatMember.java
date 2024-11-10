package org.example.chatservice.chatRoom.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatMember {
    private final String id;
    private final String userId;

    private final int lastMessageIdx;
    private final long createdAt;
    private final List<ChatRoomMembership> chatRoomMemberships;
    @Builder
    public ChatMember(String id, String userId, long createdAt, int lastMessageIdx,List<ChatRoomMembership> chatRoomMemberships) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.lastMessageIdx = lastMessageIdx;
        this.chatRoomMemberships = (chatRoomMemberships != null) ? new ArrayList<>(chatRoomMemberships) : new ArrayList<>();
    }

    public static ChatMember createChatMember(String userId, UuidHolder uuidHolder, ClockHolder clockHolder){
        return ChatMember.builder()
                .id(uuidHolder.random())
                .userId(userId)
                .createdAt(clockHolder.mills())
                .lastMessageIdx(0)
                .chatRoomMemberships(new ArrayList<>())
                .build();
    }

    public void joinChatRoom(String roomId, ClockHolder clockHolder){
        boolean alreadyMember = chatRoomMemberships.stream()
                .anyMatch(membership -> membership.getRoomId().equals(roomId));
        if (!alreadyMember) {
            ChatRoomMembership membership = new ChatRoomMembership(roomId, clockHolder.mills());
            chatRoomMemberships.add(membership);
        }
    }

    public void leaveChatRoom(String roomId) {
        chatRoomMemberships.removeIf(membership -> membership.getRoomId().equals(roomId));
    }

    public boolean isMemberOf(String roomId) {
        return chatRoomMemberships.stream()
                .anyMatch(membership -> membership.getRoomId().equals(roomId));
    }

    public List<ChatRoomMembership> getChatRoomMemberships() {
        return new ArrayList<>(chatRoomMemberships);
    }


}
