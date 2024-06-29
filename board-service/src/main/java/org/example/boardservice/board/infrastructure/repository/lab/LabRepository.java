package org.example.boardservice.board.infrastructure.repository.lab;

import org.example.boardservice.board.infrastructure.entity.LabEntity;

import java.util.List;

public interface LabRepository {

    List<LabEntity> findAll();
}
