package org.example.boardservice.board.dto.request.comment;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDeleteRequest {
    private String commentId;
    private String userId;

    @Nullable
    private String recommentId;
}
