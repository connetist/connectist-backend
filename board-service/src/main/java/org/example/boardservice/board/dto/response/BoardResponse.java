package org.example.boardservice.board.dto.response;

import lombok.Getter;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;

import java.util.List;


@Getter
public class BoardResponse {
    private Board board;
    private List<Comment> commentList;

    public BoardResponse(Board board, List<Comment> commentList) {
        this.board=board;
        this.commentList = commentList;
    }

}
