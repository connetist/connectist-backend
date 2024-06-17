package org.example.chatservice.chatRoom.dto.Request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateChatRoomRequest {
    private String adminId;
    private String title;
    private String departure;
    private String destination;
    private long timeTaken;
    private long startTime;
    private int fee;

    public CreateChatRoomRequest(String adminId, String title, String departure, String destination, long timeTaken, long startTime, int fee) {
        this.adminId = adminId;
        this.title = title;
        this.departure = departure;
        this.destination = destination;
        this.timeTaken = timeTaken;
        this.startTime = startTime;
        this.fee = fee;
    }



}
