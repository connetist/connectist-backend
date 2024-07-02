package org.example.boardservice.board.dto.request;

import lombok.Getter;

@Getter
public class LabSchoolAndMajorRequest {
    private String userId;
    private String School;
    private String major;

    public LabSchoolAndMajorRequest(String userId, String School, String major) {
        this.userId = userId;
        this.School = School;
        this.major = major;
    }
}
