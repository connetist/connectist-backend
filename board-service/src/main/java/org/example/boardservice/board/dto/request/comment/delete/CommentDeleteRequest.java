package org.example.boardservice.board.dto.request.comment.delete;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDeleteRequest {
    private String commentId;
    private String userId;
}
