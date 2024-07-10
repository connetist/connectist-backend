package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.boardservice.board.domain.Comment;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "comments")
@Setter
@Getter
@NoArgsConstructor
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


    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<RecommentEntity> recommentEntityList;


    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<LikeEntity> likeEntityList;

    public static CommentEntity from(Comment comment){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(comment.getId());
        commentEntity.setBoardId(comment.getBoardId());
        commentEntity.setUserId(comment.getUserId());
        commentEntity.setContents(comment.getContents());
        commentEntity.setDeleted(comment.isDeleted());
        commentEntity.setCreatedAt(comment.getCreatedAt());
        commentEntity.setDeletedAt(comment.getDeletedAt());

        CommentEntity commentEntityRelations = CommentEntity.addCommentRelations(commentEntity, comment);
        commentEntity.setRecommentEntityList(commentEntityRelations.getRecommentEntityList());
        commentEntity.setLikeEntityList(commentEntityRelations.getLikeEntityList());

       return commentEntity;

    }

    private static CommentEntity addCommentRelations(CommentEntity commentEntity, Comment comment) {
        commentEntity.setRecommentEntityList(comment.getRecomments().stream().map(
                recomment -> RecommentEntity.from(recomment, commentEntity)
        ).collect(Collectors.toList()));
        commentEntity.setLikeEntityList(comment.getLikes().stream().map(
                like -> LikeEntity.ofCommentEntity(like, commentEntity)
        ).collect(Collectors.toList()));
        return commentEntity;
    }

    public Comment toModel(){
        return Comment.builder()
                .id(id)
                .boardId(boardId)
                .userId(userId)
                .contents(contents)
                .deleted(deleted)
                .likes(likeEntityList.stream().map(LikeEntity::toModel).collect(Collectors.toList()))
                .recomments(recommentEntityList.stream().map(RecommentEntity::toModel).collect(Collectors.toList()))
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }


}
