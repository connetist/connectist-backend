package org.example.boardservice.board.service.lab;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.dto.request.lab.LabAddStarRequest;
import org.example.boardservice.board.dto.request.lab.LabRemoveStarRequest;
import org.example.boardservice.board.dto.request.lab.LabUpdateStarRequest;

import java.util.List;

public interface LabService {

    Lab findById(String id);
    List<Lab> findAllBySchoolAndMajor(String school, String major);
    Lab addStar(LabAddStarRequest rq);
    Lab updateStar(LabUpdateStarRequest rq);
    Lab removeStar(LabRemoveStarRequest rq);
}
