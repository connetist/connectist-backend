package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Like;

import java.util.List;

@Entity
@Data
@Table(name = "boards")
public class BoardEntity {
    @Id
    @Column(name = "board_id")
    private String id;
    private String userId;
//    private String labId;
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntities;
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL)
    private List<LikeEntity> likeEntities;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "lab_id", nullable = true)
    private LabEntity labEntity;


}
