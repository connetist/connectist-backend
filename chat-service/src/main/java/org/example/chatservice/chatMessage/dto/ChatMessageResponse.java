package org.example.chatservice.chatMessage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponse {
    private String id;
    private String roomId;
    private String senderId;
    private String content;
    private long createdAt;
}
