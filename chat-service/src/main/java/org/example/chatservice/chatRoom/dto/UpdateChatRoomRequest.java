package org.example.chatservice.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateChatRoomRequest {
    private String id;
    private String title;
    private String departure;
    private String destination;
    private String timeTaken;
    private String startTime;
    private int fee;
}