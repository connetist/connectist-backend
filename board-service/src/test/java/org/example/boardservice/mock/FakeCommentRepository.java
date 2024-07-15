package org.example.boardservice.mock;

import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FakeCommentRepository implements CommentReposotiry {
    private final List<Board> boards  = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Comment findById(String id) {

        return null;
    }

    @Override
    public List<Comment> findByBoardId(String boardId) {
        return List.of();
    }

    @Override
    public Comment saveComment(Comment comment) {
        return null;
    }

    @Override
    public Recomment findRecommentById(String commentId, String recommentId) {
        return null;
    }
}
