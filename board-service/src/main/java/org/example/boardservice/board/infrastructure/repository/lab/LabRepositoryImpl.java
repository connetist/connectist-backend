package org.example.boardservice.board.infrastructure.repository.lab;


import lombok.RequiredArgsConstructor;
import org.example.boardservice.board.infrastructure.entity.LabEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LabRepositoryImpl implements LabRepository {
    private final LabJpaRepository labJpaRepository;

    @Override
    public List<LabEntity> findAll(){
        return labJpaRepository.findAll();
    }

}
