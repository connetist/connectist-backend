package org.example.chatservice.domain;

public class ChatMember {
    private final String id;
    private final String userId;
    private final int createdAt;

    public ChatMember(String id, String userId, int createdAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
