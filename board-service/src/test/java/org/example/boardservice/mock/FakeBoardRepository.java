package org.example.boardservice.mock;

import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FakeBoardRepository implements BoardRepository {
    private final List<Board> boards  = Collections.synchronizedList(new ArrayList<>());
    @Override
    public Board save(Board board) {
        boards.add(board);
        return board;
    }
    //
    @Override
    public List<Board> findAllByLabId(String labId) {
        return boards.stream()
                .filter(board -> board.getLabId().equals(labId))
                .collect(Collectors.toList());
    }

    @Override
    public Board findByBoardId(String boardId) {
        return boards.stream()
                .filter(board->board.getId().equals(boardId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Board deleteBoardById(String boardId) {
        Board boardToDelete = findByBoardId(boardId);
        if (boardToDelete != null) {
            boards.remove(boardToDelete);
        }
        return boardToDelete;
    }
}
