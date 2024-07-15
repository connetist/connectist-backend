package org.example.boardservice.board.dto.request.comment.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentRequest {
    private String boardId;
    private String userId;
    private String contents;

    @Builder
    public CommentRequest(String boardId, String userId, String contents) {
        this.boardId = boardId;
        this.userId = userId;
        this.contents = contents;

    }
}
