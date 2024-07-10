package org.example.boardservice.board.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Like;

@Entity
@Table(name = "likes")
@Getter
@Setter
public class LikeEntity {
    @Id
    private String id;
    private String userId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name="comment_id")
    private CommentEntity comment;

    public static LikeEntity ofBoardEntity(Like like, BoardEntity boardEntity) {
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setId(like.getId());
        likeEntity.setUserId(like.getUserId());
        likeEntity.setBoard(boardEntity);
        return likeEntity;
    }

    public static LikeEntity ofCommentEntity(Like like, CommentEntity commentEntity) {
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setComment(commentEntity);
        likeEntity.setId(like.getId());
        likeEntity.setUserId(like.getUserId());
        return likeEntity;
    }

    public Like toModel() {
        return Like.builder()
                .id(id)
                .userId(userId)
                .build();
    }

}