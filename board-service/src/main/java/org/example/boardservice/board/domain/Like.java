package org.example.boardservice.board.domain;


import lombok.Builder;
import lombok.Getter;

@Getter
public class Like {
    private String id;
    private String boardId;
    private String commentId;
    private String userId;
    private long createdAt;

    @Builder
    public Like(String id, String boardId, String commentId, String userId, long createdAt) {
        this.id = id;
        this.boardId = boardId;
        this.commentId = commentId;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
