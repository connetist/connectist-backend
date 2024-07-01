package org.example.boardservice.board.infrastructure.repository.board;


import lombok.RequiredArgsConstructor;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.infrastructure.entity.BoardEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

    private final BoardJpaRepository boardJpaRepository;

    @Transactional
    @Override
    public List<Board> findAllByLabId(String labId) {
        return boardJpaRepository.findAllByLabId(labId)
                .stream()
                .map(BoardEntity::toModel)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Board findByBoardId(String boardId){
        return boardJpaRepository.findBoardById(boardId).toModel();
    }

    @Transactional
    @Override
    public Board deleteBoardById(String boardId){
        return boardJpaRepository.deleteBoardById(boardId).toModel();
    }


}
