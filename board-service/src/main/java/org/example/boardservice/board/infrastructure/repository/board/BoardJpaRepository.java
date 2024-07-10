package org.example.boardservice.board.infrastructure.repository.board;


import org.example.boardservice.board.infrastructure.entity.BoardEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface BoardJpaRepository extends JpaRepository<BoardEntity,String> , JpaSpecificationExecutor<BoardEntity> {

    @Override
    List<BoardEntity> findAll(Specification<BoardEntity> spec);
    BoardEntity deleteBoardById(String boardId);
    BoardEntity findBoardById(String boardId);
}
