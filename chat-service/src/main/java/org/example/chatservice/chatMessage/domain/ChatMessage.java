package org.example.chatservice.chatMessage.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.chatservice.chatMessage.dto.CreateChatMessageRequest;
import org.example.chatservice.utils.ClockHolder;
import org.example.chatservice.utils.UuidHolder;

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


    public static ChatMessage createChatMessage(CreateChatMessageRequest rq, UuidHolder uuidHolder, ClockHolder clockHolder){
        return ChatMessage.builder().
                id(uuidHolder.random()).
                userId(rq.getUserId()).
                roomId(rq.getRoomId()).
                content(rq.getContent()).
                createdAt(clockHolder.mills()).
                build();

    }
    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", roomId='" + roomId + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", userId='" + userId + '\'' +
                '}';
    }


}
