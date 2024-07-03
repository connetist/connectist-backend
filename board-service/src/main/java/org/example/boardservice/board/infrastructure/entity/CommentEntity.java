package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import jakarta.websocket.OnError;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Table(name = "comments")
@Setter
@Getter
public class CommentEntity {
    @Id
    @Column(name = "comment_id")
    private String id;
    private String boardId;
    private String userId;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;


    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private List<RecommentEntity> recommentEntityList;


    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private List<LikeEntity> likeEntityList;

    public CommentEntity() {

    }

    public static CommentEntity from(Comment comment){
       CommentEntity commentEntity = new CommentEntity();
       commentEntity.id = comment.getId();
       commentEntity.boardId = comment.getBoardId();
       commentEntity.userId = comment.getUserId();
       commentEntity.contents = comment.getContents();
       commentEntity.createdAt = comment.getCreatedAt();
       commentEntity.deletedAt = comment.getDeletedAt();

       return commentEntity;

    }

    public Comment toModel(){
        return Comment.builder()
                .id(id)
                .boardId(boardId)
                .userId(userId)
                .contents(contents)
                .deleted(deleted)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }



}
