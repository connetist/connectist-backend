package org.example.boardservice.lab.service;

import lombok.Builder;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.board.dto.request.LabAddStarRequest;
import org.example.boardservice.board.dto.request.LabRemoveStarRequest;
import org.example.boardservice.board.dto.request.LabUpdateStarRequest;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;
import org.example.boardservice.board.service.LabService;
import org.example.boardservice.board.service.LabServiceImpl;
import org.example.boardservice.lab.domain.LabDomainTest;
import org.example.boardservice.mock.FakeLabRepository;
import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;

import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.ArrayList;
import java.util.List;

public class LabServiceTest {
    private LabServiceImpl labService;
    private LabRepository labRepository;

    @BeforeEach
    void init(){
        labRepository = new FakeLabRepository();
        UuidHolder uuidHolder = new TestUuidHolder("testId");
        ClockHolder clockHolder = new TestClockHolder(1000);

        labService = LabServiceImpl.builder()
                .labRepository(labRepository)
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
        labRepository.save(lab);
    }
////
    @Test
    void 연구실_아이디로_특정_연구실_조회(){
        Lab lab = labService.findById("testId");

        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().size()).isEqualTo(1);

    }

    @Test
    void 연구실을_학교와_전공을_통해서_전체_조회(){
        List<Lab> labs = labService.findAllBySchoolAndMajor("DGIST","EE");
        assertThat(labs.size()).isEqualTo(1);
        assertThat(labs.get(0).getLabId()).isEqualTo("testId");
        assertThat(labs.get(0).getSchool()).isEqualTo("DGIST");
        assertThat(labs.get(0).getMajor()).isEqualTo("EE");
        assertThat(labs.get(0).getProfessor()).isEqualTo("prof");
        assertThat(labs.get(0).getContents()).isEqualTo("testContents");
        assertThat(labs.get(0).getCreatedAt()).isEqualTo(10000);
        assertThat(labs.get(0).getStars().size()).isEqualTo(1);
    }

    @Test
    void 연구실_별점_추가(){
        LabAddStarRequest rq = new LabAddStarRequest("testUserId2", "testId", 10);
        Lab lab = labService.addStar(rq);

        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().size()).isEqualTo(2);
    }

    @Test
    void 연구실_별점_변경(){
        LabUpdateStarRequest rq = new LabUpdateStarRequest("testUserId", "testId", 10);
        Lab lab = labService.updateStar(rq);


        System.out.println(lab.getLabId());
        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().size()).isEqualTo(1);
        assertThat(lab.getStars().get(0).getStarCount()).isEqualTo(10);
    }

    @Test
    void 연구실_별점_삭제(){
        LabRemoveStarRequest rq = new LabRemoveStarRequest("testUserId", "testId");
        Lab lab = labService.removeStar(rq);

        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().size()).isEqualTo(0);

    }
}
