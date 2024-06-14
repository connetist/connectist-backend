package org.example.chatservice.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class UpdateMemberRequest {
    private String userId;
    private String roomId;

}
