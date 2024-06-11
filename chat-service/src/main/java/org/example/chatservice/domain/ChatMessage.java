package org.example.chatservice.domain;

public class ChatMessage {
    private final String id;
    private final String userId;

    private final String roomId;
    private final String content;
    private final int createdAt;

    public ChatMessage(String id, String userId, String content, int createdAt, String roomId) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
