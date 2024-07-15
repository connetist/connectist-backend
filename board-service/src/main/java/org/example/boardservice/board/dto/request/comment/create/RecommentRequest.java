package org.example.boardservice.board.dto.request.comment.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommentRequest {
    private String boardId;
    private String userId;
    private String contents;
    private String commentId;

    @Builder
    public RecommentRequest(String boardId, String userId, String contents, String commentId) {
        this.boardId = boardId;
        this.userId = userId;
        this.contents = contents;
        this.commentId = commentId;

    }
}
