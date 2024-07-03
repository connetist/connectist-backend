package org.example.boardservice.board.dto.request;

import lombok.Getter;

@Getter
public class LabUpdateStarRequest {
    private String userId;
    private String labId;
    private int starCount;

    public LabUpdateStarRequest(String userId, String labId, int starCount) {
        this.userId = userId;
        this.labId = labId;
        this.starCount = starCount;
    }
}
