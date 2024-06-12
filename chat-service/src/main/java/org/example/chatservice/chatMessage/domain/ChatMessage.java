package org.example.chatservice.chatMessage.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessage {
    private final String id;
    private final String userId;

    private final String roomId;
    private final String content;
    private final long createdAt;

    @Builder
    public ChatMessage(String id, String userId, String content, long createdAt, String roomId) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.content = content;
        this.createdAt = createdAt;
    }


}
