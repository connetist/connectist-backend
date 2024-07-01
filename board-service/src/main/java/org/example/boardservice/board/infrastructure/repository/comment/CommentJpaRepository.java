package org.example.boardservice.board.infrastructure.repository.comment;

import org.example.boardservice.board.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity,String> {
    List<CommentEntity> findByBoardId(String boardId);
}
