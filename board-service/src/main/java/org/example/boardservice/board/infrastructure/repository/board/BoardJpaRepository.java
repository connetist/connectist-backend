package org.example.boardservice.board.infrastructure.repository.board;


import org.example.boardservice.board.infrastructure.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardEntity,String> {

    List<BoardEntity> findAll();

}
