package org.example.boardservice.board.infrastructure.repository.comment;

import jakarta.persistence.criteria.Join;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.infrastructure.entity.CommentEntity;
import org.example.boardservice.board.infrastructure.entity.RecommentEntity;
import org.springframework.data.jpa.domain.Specification;

@Slf4j
public class CommentSpecs {
    private CommentSpecs() {
    }

    public static Specification<CommentEntity> exceptDeleted() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.isFalse(root.get("deleted")));
    }

    public static Specification<CommentEntity> byCommentId(String commentId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), commentId));
    }

    public static Specification<CommentEntity> byBoardId(String boardId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("boardId"), boardId));
    }

    public static Specification<CommentEntity> exceptRecommentDeleted() {
        return (root, query, criteriaBuilder) ->
        {
            Join<CommentEntity, RecommentEntity> recommentJoin = root.join("recommentEntityList");
            return criteriaBuilder.isFalse(recommentJoin.get("deleted"));

        };
    }
}
