package org.example.chatservice.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class UpdateMemberRequest {
    private String userId;

    public UpdateMemberRequest(String userId, String roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    private String roomId;

}
