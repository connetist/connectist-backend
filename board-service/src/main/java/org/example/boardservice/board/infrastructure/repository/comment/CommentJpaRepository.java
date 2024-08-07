package org.example.boardservice.board.infrastructure.repository.comment;

import org.example.boardservice.board.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<CommentEntity,String>, JpaSpecificationExecutor<CommentEntity> {
    List<CommentEntity> findAllByBoardId(String boardId);
    CommentEntity findCommentEntityById(String commentId);
    CommentEntity deleteCommentEntityById(String commentId);
    Optional<CommentEntity> findById(String CommentId);

    List<CommentEntity> findAll();
}
