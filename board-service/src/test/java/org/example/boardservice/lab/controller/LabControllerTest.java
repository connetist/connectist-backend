package org.example.boardservice.lab.controller;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.board.dto.request.lab.LabAddStarRequest;
import org.example.boardservice.board.dto.request.lab.LabRemoveStarRequest;
import org.example.boardservice.board.dto.request.lab.LabUpdateStarRequest;
import org.example.boardservice.board.dto.response.RestResponse;
import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestContainer;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
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
        ResponseEntity<RestResponse<List<Lab>>> result = testContainer.labController.getAllLab("DGIST", "EE");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getData().size()).isEqualTo(1);
    }

    @Test
    public void 특정_연구실_조회(){
        ResponseEntity<RestResponse<Lab>> result = testContainer.labController.getLab("testId");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getData().getLabId()).isEqualTo("testId");
        assertThat(result.getBody().getData().getSchool()).isEqualTo("DGIST");
        assertThat(result.getBody().getData().getMajor()).isEqualTo("EE");
        assertThat(result.getBody().getData().getProfessor()).isEqualTo("prof");
        assertThat(result.getBody().getData().getContents()).isEqualTo("testContents");
    }

    @Test
    public void 연구실_평점_매기기(){
        LabAddStarRequest rq = new LabAddStarRequest("testUserId2", "testId", 10);
        ResponseEntity<RestResponse<Lab>> result = testContainer.labController.addStar(rq);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getData().getLabId()).isEqualTo("testId");
        assertThat(result.getBody().getData().getSchool()).isEqualTo("DGIST");
        assertThat(result.getBody().getData().getMajor()).isEqualTo("EE");
        assertThat(result.getBody().getData().getProfessor()).isEqualTo("prof");
        assertThat(result.getBody().getData().getContents()).isEqualTo("testContents");
        assertThat(result.getBody().getData().getStars().size()).isEqualTo(2);
    }

    @Test
    public void 연구실_평점_변경(){
        LabUpdateStarRequest rq = new LabUpdateStarRequest("testUserId", "testId", 10);
        ResponseEntity<RestResponse<Lab>> result = testContainer.labController.updateStar(rq);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getData().getLabId()).isEqualTo("testId");
        assertThat(result.getBody().getData().getSchool()).isEqualTo("DGIST");
        assertThat(result.getBody().getData().getMajor()).isEqualTo("EE");
        assertThat(result.getBody().getData().getProfessor()).isEqualTo("prof");
        assertThat(result.getBody().getData().getContents()).isEqualTo("testContents");
        assertThat(result.getBody().getData().getStars().get(0).getStarCount()).isEqualTo(10);
    }

    @Test
    public void 연구실_평점_삭제(){
        LabRemoveStarRequest rq = new LabRemoveStarRequest("testUserId", "testId");
        ResponseEntity<RestResponse<Lab>> result = testContainer.labController.removeStar(rq);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getData().getLabId()).isEqualTo("testId");
        assertThat(result.getBody().getData().getSchool()).isEqualTo("DGIST");
        assertThat(result.getBody().getData().getMajor()).isEqualTo("EE");
        assertThat(result.getBody().getData().getProfessor()).isEqualTo("prof");
        assertThat(result.getBody().getData().getContents()).isEqualTo("testContents");
        assertThat(result.getBody().getData().getStars().size()).isEqualTo(0);

    }

}
