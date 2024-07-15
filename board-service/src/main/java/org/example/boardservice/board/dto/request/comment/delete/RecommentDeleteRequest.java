package org.example.boardservice.board.dto.request.comment.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter

public class RecommentDeleteRequest {
    private String commentId;
    private String userId;
    private String recommentId;

    @Builder
    public RecommentDeleteRequest(String commentId, String userId, String recommentId) {
        this.commentId = commentId;
        this.userId = userId;
        this.recommentId = recommentId;

    }
}
