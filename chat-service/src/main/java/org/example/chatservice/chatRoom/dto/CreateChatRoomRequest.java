package org.example.chatservice.chatRoom.dto;

import lombok.Getter;

@Getter
public class CreateChatRoomRequest {
    private String adminId;
    private String title;
    private String departure;
    private String destination;
    private long timeTaken;
    private long startTime;
    private int fee;
}
