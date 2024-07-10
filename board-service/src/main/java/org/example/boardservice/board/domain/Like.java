package org.example.boardservice.board.domain;



import lombok.Builder;
import lombok.Getter;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;

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

    public static Like ofBoard(String boardId, String userId, UuidHolder uuidHolder, ClockHolder clockHolder) {
        return Like.builder()
                .boardId(boardId)
                .userId(userId)
                .createdAt(clockHolder.mills())
                .id(uuidHolder.random())
                .build();
    }

    public static Like ofComment(String commentId, String userId, UuidHolder uuidHolder, ClockHolder clockHolder) {
        return Like.builder()
                .commentId(commentId)
                .userId(userId)
                .id(uuidHolder.random())
                .createdAt(clockHolder.mills())
                .build();
    }
}
