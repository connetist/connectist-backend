package org.example.boardservice.board.infrastructure.repository.comment;

import org.example.boardservice.board.domain.Comment;

import java.util.List;

public interface CommentReposotiry {

    Comment findById(String id);

    List<Comment> findByBoardId(String boardId);

//    Comment deleteComment(Comment comment);

    List<Comment> saveComment(Comment comment);

//    Comment saveReComment(Comment comment, Recomment recomment);

//    Comment deleteReComment(Comment comment, Recomment recomment);

}
