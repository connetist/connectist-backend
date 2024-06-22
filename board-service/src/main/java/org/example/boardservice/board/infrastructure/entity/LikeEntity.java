package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "likes")
public class LikeEntity {
    @Id
    private String id;

    private int likeUnit;

//    @ManyToOne
//    @JoinColumn(name = "lab_id", nullable = true)
//    private LabEntity labEntity;

    @ManyToOne
    @JoinColumn(name= "board_id",nullable = true)
    private BoardEntity boardEntity;

    @ManyToOne
    @JoinColumn(name= "comment_id", nullable = true)
    private CommentEntity commentEntity;
}
