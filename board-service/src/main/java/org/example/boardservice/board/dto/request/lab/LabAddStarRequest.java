package org.example.boardservice.board.dto.request.lab;


import lombok.Getter;

@Getter
public class LabAddStarRequest {
    private String userId;
    private String labId;
    private int starCount;
    public LabAddStarRequest(String userId, String labId, int starCount) {
        this.userId = userId;
        this.labId = labId;
        this.starCount = starCount;
    }
}
