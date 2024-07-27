package org.example.boardservice.board.infrastructure.repository.board;

import lombok.RequiredArgsConstructor;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.infrastructure.entity.BoardEntity;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.springframework.data.jpa.domain.Specification;
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
    public Board save(Board board) {
        return boardJpaRepository.save(BoardEntity.from(board)).toModel();
    }

    @Transactional
    @Override
    public List<Board> findAllByLabId(String labId) {

        Specification<BoardEntity> spec = Specification.where(BoardSpecs.exceptDeleted(false)).and(BoardSpecs.byLabId(labId));
        try {
            return boardJpaRepository.findAll(spec)
                    .stream()
                    .map(BoardEntity::toModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GlobalException(ResultCode.LAB_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public Board findByBoardId(String boardId){
        try {
            return boardJpaRepository.findBoardById(boardId).toModel();
        } catch (Exception e) {
            throw new GlobalException(ResultCode.BOARD_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public Board deleteBoardById(String boardId){
        try {
            return boardJpaRepository.deleteBoardById(boardId).toModel();
        } catch (Exception e) {
            throw new GlobalException(ResultCode.BOARD_NOT_FOUND);
        }
    }



}
