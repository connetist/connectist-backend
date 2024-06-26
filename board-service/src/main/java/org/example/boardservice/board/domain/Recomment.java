package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Recomment {
    private String id;
    private String commentId;
    private String userId;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;

    @Builder
    public Recomment(String id, String commentId, String userId, String contents, boolean deleted, long createdAt, long deletedAt) {
        this.id = id;
        this.commentId = commentId;
        this.userId = userId;
        this.contents = contents;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }


}
