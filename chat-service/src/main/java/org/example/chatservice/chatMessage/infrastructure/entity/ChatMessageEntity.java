package org.example.chatservice.chatMessage.infrastructure.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.chatservice.chatMessage.domain.ChatMessage;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "chat_message")
public class ChatMessageEntity {
    private  String id;
    private  String userId;
    private String roomId;
    private String content;
    private  long createdAt;

    public static ChatMessageEntity from(ChatMessage chatMessage){
        return ChatMessageEntity.builder()
                .id(chatMessage.getId())
                .userId(chatMessage.getUserId())
                .roomId(chatMessage.getRoomId())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    public ChatMessage toModel(){
        return ChatMessage.builder()
                .id(id)
                .userId(userId)
                .roomId(roomId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
