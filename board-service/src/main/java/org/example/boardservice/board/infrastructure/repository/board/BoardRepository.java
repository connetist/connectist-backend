package org.example.boardservice.board.infrastructure.repository.board;

import org.example.boardservice.board.domain.Board;

import java.util.List;

public interface BoardRepository {

    List<Board> findAll(int offset, int limit);

}
