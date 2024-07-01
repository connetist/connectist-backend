package org.example.boardservice.board.service;

import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.dto.FindBoardResponse;

import java.util.List;

public interface BoardService {

    List<Board> getAllByLabId(String labId);
    FindBoardResponse getBoardById(String boardId);
    Board createBoard(String userId, String labId, String contents);
    Board deleteBoard(String boardId);
}
