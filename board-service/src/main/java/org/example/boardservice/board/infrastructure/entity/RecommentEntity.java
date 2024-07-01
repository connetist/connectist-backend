package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Recomment;

@Getter
@Setter
@Entity
@Table(name = "recomments")
public class RecommentEntity {
    @Id
    @Column(name = "recomment_id")
    private String id;

    private String userId;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    public static RecommentEntity from(Recomment recomment) {
        RecommentEntity recommentEntity = new RecommentEntity();
        recommentEntity.id = recomment.getId();
//        recommentEntity.commentId = recomment.getCommentId();
        recommentEntity.userId = recomment.getUserId();
        recommentEntity.contents = recomment.getContents();
        recommentEntity.deleted = recomment.isDeleted();
        recommentEntity.createdAt = recomment.getCreatedAt();
        recommentEntity.deletedAt = recomment.getDeletedAt();
        return recommentEntity;
    }

    public Recomment toModel(){
        return Recomment.builder()
                .id(id)
                .userId(userId)
                .contents(contents)
                .deleted(deleted)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();

    }

}
