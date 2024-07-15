package org.example.boardservice.board.service.board;

import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.dto.request.board.BoardDeleteRequest;
import org.example.boardservice.board.dto.request.board.BoardLikeRequest;
import org.example.boardservice.board.dto.request.board.BoardRequest;
import org.example.boardservice.board.dto.response.BoardResponse;

import java.util.List;

public interface BoardService {

    List<Board> getAllByLabId(String labId);
    Board getBoardById(String boardId);
    Board createBoard(BoardRequest boardRequest);
    Board deleteBoard(BoardDeleteRequest boardDeleteRequest);

    Board addLikeBoard(String boardId, BoardLikeRequest boardLikeRequest);
    Board removeLikeBoard(String boardId, BoardLikeRequest boardLikeRequest);

}
