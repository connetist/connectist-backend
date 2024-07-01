package org.example.boardservice.board.infrastructure.repository.lab;

import org.example.boardservice.board.infrastructure.entity.LabEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabJpaRepository extends JpaRepository<LabEntity,String> {
}
