package org.example.boardservice.board.service;


import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.board.infrastructure.entity.LabEntity;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@Slf4j
public class LabServiceImpl implements LabService{

    private LabRepository labRepository;

    public LabServiceImpl(LabRepository labRepository) {
        this.labRepository = labRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public Lab findById(String id){;
        return labRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Lab> findAllBySchoolAndMajor(String school, String major){
        return labRepository.findAllBySchoolandMajor(school,major);
    }


    @Transactional
    @Override
    public Lab addStar(String userId, int starCount,String labId){
        Lab lab = labRepository.findById(labId);
        lab.addStar(userId, labId, starCount);
        lab = labRepository.save(lab);

        return lab;
    }

    @Transactional
    @Override
    public Lab removeStar(String userId, int starCount,String labId){
        Lab lab = labRepository.findById(labId);
        lab.removeStar(userId);
        lab = labRepository.save(lab);
        return lab;
    }

    @Transactional
    @Override
    public Lab updateStar(String userId, int starCount,String labId){
        Lab lab = labRepository.findById(labId);
        lab.updateStar(userId,starCount);
        lab = labRepository.save(lab);
        return lab;
    }





}
