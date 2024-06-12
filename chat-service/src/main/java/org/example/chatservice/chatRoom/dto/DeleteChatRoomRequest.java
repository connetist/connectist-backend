package org.example.chatservice.chatRoom.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteChatRoomRequest {
    private String roomId;
    private String userId;
}
