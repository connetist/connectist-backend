package org.example.boardservice.board.infrastructure.repository.comment;

import org.example.boardservice.board.domain.Comment;

import java.util.List;

public interface CommentReposotiry {

    Comment findById(String id);

    List<Comment> findByBoardId(String boardId);

    List<Comment> saveComment(Comment comment);

}
