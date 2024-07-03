package org.example.boardservice.board.service;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.dto.request.LabAddStarRequest;
import org.example.boardservice.board.dto.request.LabRemoveStarRequest;
import org.example.boardservice.board.dto.request.LabUpdateStarRequest;

import java.util.List;

public interface LabService {

    Lab findById(String id);
    List<Lab> findAllBySchoolAndMajor(String school, String major);
    Lab addStar(LabAddStarRequest rq);
    Lab updateStar(LabUpdateStarRequest rq);
    Lab removeStar(LabRemoveStarRequest rq);
}
