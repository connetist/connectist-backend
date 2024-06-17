package org.example.chatservice.chatMessage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
//@Setter
public class CreateChatMessageRequest {
    private String userId;
    private String content;
    private String roomId;

    public CreateChatMessageRequest(String userId, String content, String roomId){
        this.userId = userId;
        this.content = content;
        this.roomId = roomId;
    }
}
