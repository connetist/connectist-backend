package org.example.boardservice.board.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BoardDeleteRequest {
    private String boardId;
    private String userId;

    @Builder
    public BoardDeleteRequest(String boardId, String userId) {
        this.boardId = boardId;
        this.userId = userId;
    }
}
