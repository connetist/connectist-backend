package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Like;

@Entity
@Table(name = "likes")
@Getter
@Setter
public class LikeEntity {
    @Id
    private String id;
    private String boardId;
    private String commentId;
    private String userId;


    public static LikeEntity from(Like like){
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.id=like.getId();
        likeEntity.boardId=like.getBoardId();
        likeEntity.userId=like.getUserId();
        likeEntity.commentId=like.getCommentId();
        return likeEntity;
    }

    public Like toModel(){
        return Like.builder()
                .id(id)
                .boardId(boardId)
                .commentId(commentId)
                .userId(userId)
                .build();
    }
}
