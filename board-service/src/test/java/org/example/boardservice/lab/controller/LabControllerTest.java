package org.example.boardservice.lab.controller;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.board.dto.response.RestResponse;
import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestContainer;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class LabControllerTest {

    private TestContainer testContainer;

    @BeforeEach
    public void init(){
        UuidHolder uuidHolder = new TestUuidHolder("testUuid");
        ClockHolder clockHolder = new TestClockHolder(10000);

        this.testContainer = TestContainer.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .build();

        List<Star> stars = new ArrayList<>();
        stars.add(new Star("testStarId", "testUserId", 5));
        Lab lab = Lab.builder()
                .labId("testId")
                .school("DGIST")
                .major("EE")
                .professor("prof")
                .contents("testContents")
                .createdAt(10000)
                .stars(stars)
                .build();
        testContainer.labRepository.save(lab);
    }

    @Test
    public void 특정_학교_전공_전체_연구실_조회(){
        ResponseEntity<RestResponse<List<Lab>>> response = testContainer.labController.getAllLab("DGIST", "EE");
//        assertThat()
    }
}
