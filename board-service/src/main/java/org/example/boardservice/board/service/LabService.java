package org.example.boardservice.board.service;

import org.example.boardservice.board.domain.Lab;

import java.util.List;

public interface LabService {

    Lab findById(String id);
    List<Lab> findAllBySchoolAndMajor(String school, String major);
    Lab addStar(String userId, int starCount, String ladId);
    Lab updateStar(String userId, int starCount, String ladId);
    Lab removeStar(String userId, int starCount, String ladId);
}
