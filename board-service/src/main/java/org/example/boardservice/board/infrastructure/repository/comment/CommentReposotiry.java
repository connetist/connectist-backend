package org.example.boardservice.board.infrastructure.repository.comment;

import org.example.boardservice.board.domain.Comment;

import java.util.List;

public interface CommentReposotiry {

    List<Comment> findByBoardId(String boardId);
}
