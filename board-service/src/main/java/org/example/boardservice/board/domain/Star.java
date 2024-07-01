package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Star {
    private final String id;
    private final String userId;
    private final String labId;

    @Builder
    public Star(String id, String userId, String labId) {
        this.id = id;
        this.userId = userId;
        this.labId = labId;
    }


}
