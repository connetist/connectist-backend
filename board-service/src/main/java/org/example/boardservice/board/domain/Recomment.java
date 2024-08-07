package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.boardservice.board.dto.request.comment.create.CommentRequest;
import org.example.boardservice.board.dto.request.comment.create.RecommentRequest;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;

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


    public static Recomment createRecomment(RecommentRequest recommentRequest, UuidHolder uuidHolder, ClockHolder clockHolder) {
        return Recomment.builder()
                .id(uuidHolder.random())
                .commentId(recommentRequest.getCommentId())
                .userId(recommentRequest.getUserId())
                .contents(recommentRequest.getContents())
                .deleted(false)
                .createdAt(clockHolder.mills())
                .deletedAt(0)
                .build();
    }

    public void deleteRecomment(ClockHolder clockHolder) {
        this.deleted = true;
        this.deletedAt = clockHolder.mills();
    }
}
