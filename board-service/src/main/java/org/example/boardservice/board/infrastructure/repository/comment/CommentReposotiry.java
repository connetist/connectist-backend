package org.example.boardservice.board.infrastructure.repository.comment;

import jakarta.persistence.LockModeType;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.example.boardservice.board.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentReposotiry {

    Comment findById(String id);

    List<Comment> findByBoardId(String boardId);

    Comment saveComment(Comment comment);

    Recomment findRecommentById(String commentId, String recommentId);
    Comment findByIdWithPessimisticLock(String id);
}
