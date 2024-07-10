package org.example.boardservice.board.dto.request.lab;

import lombok.Getter;

@Getter
public class LabRemoveStarRequest {
    private String userId;
    private String labId;

    public LabRemoveStarRequest(String userId, String labId) {
        this.userId = userId;
        this.labId = labId;
    }
}
