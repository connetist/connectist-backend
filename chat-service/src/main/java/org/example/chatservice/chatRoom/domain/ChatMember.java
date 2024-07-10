package org.example.chatservice.chatRoom.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;

import java.time.Clock;

@Getter
public class ChatMember {
    private final String id;
    private final String userId;

    private final int lastMessageIdx;
    private final long createdAt;

    @Builder
    public ChatMember(String id, String userId, long createdAt, int lastMessageIdx) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.lastMessageIdx = lastMessageIdx;
    }

    static ChatMember createChatMember(String userId, UuidHolder uuidHolder, ClockHolder clockHolder){
        return ChatMember.builder()
                .id(uuidHolder.random())
                .userId(userId)
                .createdAt(clockHolder.mills())
                .lastMessageIdx(0)
                .build();
    }
}
