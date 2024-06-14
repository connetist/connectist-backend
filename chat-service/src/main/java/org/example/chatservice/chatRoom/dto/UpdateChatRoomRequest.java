package org.example.chatservice.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateChatRoomRequest {
    private String id;

    public UpdateChatRoomRequest(String id, String title, String departure, String destination, long timeTaken, long startTime, int fee) {
        this.id = id;
        this.title = title;
        this.departure = departure;
        this.destination = destination;
        this.timeTaken = timeTaken;
        this.startTime = startTime;
        this.fee = fee;
    }

    private String title;
    private String departure;
    private String destination;
    private long timeTaken;
    private long startTime;
    private int fee;
}