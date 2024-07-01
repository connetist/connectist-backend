package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.boardservice.board.infrastructure.entity.RecommentEntity;

import java.util.List;
@Getter
public class Recomment {
    private final String id;
    private final String boardId;
    private final String userId;
    private final String contents;
    private final boolean deleted;
    private final long createdAt;
    private final long deletedAt;

    @Builder
    public Recomment(String id, String boardId, String userId, String contents, boolean deleted, long createdAt, long deletedAt) {
        this.id = id;
        this.boardId = boardId;
        this.userId = userId;
        this.contents = contents;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

}
