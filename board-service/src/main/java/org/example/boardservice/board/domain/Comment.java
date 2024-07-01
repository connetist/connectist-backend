package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
public class Comment {
    private String id;
    private String boardId;
    private String userId;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;
    @Builder
    public Comment(String id, String boardId, String userId, String contents, boolean deleted, long createdAt, long deletedAt) {
        this.id = id;
        this.boardId = boardId;
        this.userId = userId;
        this.contents = contents;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }


}
