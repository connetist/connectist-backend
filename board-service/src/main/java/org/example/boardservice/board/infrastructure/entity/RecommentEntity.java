package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "recomments")
public class RecommentEntity {
    @Id
    @Column(name = "recomment_id")
    private String id;

//    private String boardId;
    private String userId;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity commentEntity;

}
