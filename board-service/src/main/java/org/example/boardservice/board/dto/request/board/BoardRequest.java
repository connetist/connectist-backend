package org.example.boardservice.board.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardRequest {
    private String userId;
    private String labId;
    private String content;

    @Builder
    public BoardRequest(String userId, String labId, String content) {
        this.userId = userId;
        this.labId = labId;
        this.content = content;
    }
}
