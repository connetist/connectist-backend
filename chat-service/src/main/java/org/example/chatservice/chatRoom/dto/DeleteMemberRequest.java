package org.example.chatservice.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteMemberRequest {
    private String userId;

    public DeleteMemberRequest(String userId, String roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    private String roomId;
}
