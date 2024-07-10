package org.example.boardservice.board.infrastructure.repository.lab;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.infrastructure.entity.LabEntity;

import java.util.List;

public interface LabRepository {

    List<Lab> findAllBySchoolandMajor(String school, String major);
    Lab findById(String id);
    Lab save(Lab lab);
}
