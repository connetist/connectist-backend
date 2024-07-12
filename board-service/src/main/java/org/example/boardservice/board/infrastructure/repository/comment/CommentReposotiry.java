package org.example.boardservice.board.infrastructure.repository.comment;

import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;

import java.util.List;

public interface CommentReposotiry {

    Comment findById(String id);

    List<Comment> findByBoardId(String boardId);

    Comment saveComment(Comment comment);

    Recomment findRecommentById(String commentId, String recommentId);
}
