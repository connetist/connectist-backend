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
    private String userId;
    private String title;
    private String departure;
    private String destination;
    private long timeTaken;
    private long startTime;
    private int fee;

    public UpdateChatRoomRequest(String id, String userId,String title, String departure, String destination, long timeTaken, long startTime, int fee) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.departure = departure;
        this.destination = destination;
        this.timeTaken = timeTaken;
        this.startTime = startTime;
        this.fee = fee;
    }


}