package org.example.boardservice.board.dto;

import lombok.Getter;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;

import java.util.List;


@Getter
public class FindBoardResponse {
    private Board board;
    private List<Comment> commentList;

    public FindBoardResponse(Board board, List<Comment> commentList) {
        this.board=board;
        this.commentList = commentList;
    }

}
