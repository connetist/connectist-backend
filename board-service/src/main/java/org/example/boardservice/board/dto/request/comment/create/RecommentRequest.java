package org.example.boardservice.board.dto.request.comment.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommentRequest {
    private String boardId;
    private String userId;
    private String contents;
    private String commentId;
}
