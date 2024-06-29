package org.example.boardservice.board.infrastructure.repository.board;


import lombok.RequiredArgsConstructor;
import org.example.boardservice.board.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

    private final BoardJpaRepository boardJpaRepository;


    @Override
    public List<Board> findAll(int offset, int limit) {
        return null;
//        return boardJpaRepository.findAll();
    }
}
