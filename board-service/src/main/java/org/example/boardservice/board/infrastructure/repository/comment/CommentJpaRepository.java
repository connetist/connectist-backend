package org.example.boardservice.board.infrastructure.repository.comment;

import jakarta.persistence.LockModeType;
import org.example.boardservice.board.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<CommentEntity,String>, JpaSpecificationExecutor<CommentEntity> {
    List<CommentEntity> findAllByBoardId(String boardId);
    CommentEntity findCommentEntityById(String commentId);
    CommentEntity deleteCommentEntityById(String commentId);
    Optional<CommentEntity> findById(String CommentId);

    List<CommentEntity> findAll();
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CommentEntity c WHERE c.id = :commentId")
    Optional<CommentEntity> findByIdWithPessimisticLock(String commentId);
}
