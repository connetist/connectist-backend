package org.example.boardservice.board.dto.request.comment.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequest {
    private String boardId;
    private String userId;
    private String contents;
}
