package org.example.boardservice.board.dto.request.comment.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter

public class CommentDeleteRequest {
    private String commentId;
    private String userId;

    @Builder
    public CommentDeleteRequest(String commentId, String userId) {
        this.commentId = commentId;
        this.userId = userId;

    }
}
