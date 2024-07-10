package org.example.boardservice.board.service;


import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.board.dto.request.LabAddStarRequest;
import org.example.boardservice.board.dto.request.LabRemoveStarRequest;
import org.example.boardservice.board.dto.request.LabUpdateStarRequest;
import org.example.boardservice.board.infrastructure.entity.LabEntity;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;
import org.example.boardservice.utils.UuidHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@Slf4j
public class LabServiceImpl implements LabService{

    private LabRepository labRepository;
    private UuidHolder uuidHolder;

    public LabServiceImpl(LabRepository labRepository, UuidHolder uuidHolder) {

        this.labRepository = labRepository;
        this.uuidHolder = uuidHolder;
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
    public Lab addStar(LabAddStarRequest rq){
        Lab lab = labRepository.findById(rq.getLabId());
        lab.addStar(rq.getUserId(),rq.getLabId(), rq.getStarCount(),uuidHolder);
        lab = labRepository.save(lab);

        return lab;
    }

    @Transactional
    @Override
    public Lab removeStar(LabRemoveStarRequest rq){
        Lab lab = labRepository.findById(rq.getLabId());
        lab.removeStar(rq.getUserId());
        lab = labRepository.save(lab);
        return lab;
    }

    @Transactional
    @Override
    public Lab updateStar(LabUpdateStarRequest rq){
        Lab lab = labRepository.findById(rq.getLabId());
        lab.updateStar(rq.getUserId(), rq.getStarCount());
        lab = labRepository.save(lab);
        return lab;
    }





}
