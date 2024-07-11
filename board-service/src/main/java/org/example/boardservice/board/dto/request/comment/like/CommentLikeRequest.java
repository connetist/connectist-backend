package org.example.boardservice.board.dto.request.comment.like;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentLikeRequest {
    private String userId;
    private String commentId;
}
