package org.example.chatservice.chatRoom.domain;

import lombok.Builder;
import lombok.Getter;

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
}
