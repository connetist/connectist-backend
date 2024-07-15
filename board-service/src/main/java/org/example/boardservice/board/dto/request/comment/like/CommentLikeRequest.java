package org.example.boardservice.board.dto.request.comment.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentLikeRequest {
    private String userId;
    private String commentId;

    @Builder
    public CommentLikeRequest(String userId, String commentId) {
        this.userId = userId;
        this.commentId = commentId;

    }
}
