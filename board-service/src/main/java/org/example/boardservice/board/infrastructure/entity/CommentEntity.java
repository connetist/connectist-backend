package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.example.boardservice.board.domain.Recomment;

import java.util.List;

@Entity
@Data
@Table(name = "comments")
public class CommentEntity {
    @Id
    @Column(name = "comment_id")
    private String id;
//    private String boardId;
    private String userId;
    private String contents;

    @OneToMany(mappedBy = "commentEntity",cascade = CascadeType.ALL)
    private List<RecommentEntity> recommentEntities;

    @OneToMany(mappedBy = "commentEntity", cascade = CascadeType.ALL)
    private List<LikeEntity> likeEntities;

    private boolean deleted;
    private long createdAt;
    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "board_id",nullable = true)
    private BoardEntity boardEntity;


}
