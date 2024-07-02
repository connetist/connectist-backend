package org.example.boardservice.board.infrastructure.repository.lab;


import lombok.RequiredArgsConstructor;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.infrastructure.entity.LabEntity;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LabRepositoryImpl implements LabRepository {
    private final LabJpaRepository labJpaRepository;

    @Override
    public Lab findById(String id){
        return labJpaRepository.findById(id).orElseThrow(()-> new GlobalException(ResultCode.LAB_NOT_FOUND)).toModel();
    }

    @Override
    public Lab save(Lab lab){
        return labJpaRepository.save(LabEntity.from(lab)).toModel();
    }



    @Override
    public List<Lab> findAllBySchoolandMajor(String school, String major){
        List<Lab> labs = labJpaRepository.findAllBySchoolAndMajor(school,major).stream().map(LabEntity::toModel).toList();
        return labs;
    }



}
