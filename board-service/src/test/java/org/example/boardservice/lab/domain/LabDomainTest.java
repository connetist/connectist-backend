package org.example.boardservice.lab.domain;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class LabDomainTest {

    private UuidHolder uuidHolder = new TestUuidHolder("testId");
    private ClockHolder clockHolder = new TestClockHolder(10000);

    @Test
    public void 연구실_도메인_빌더(){
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
        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().size()).isEqualTo(1);
    }

    @Test
    public void 연구실_별점_추가(){
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
//        Star newStar = new Star("testStarId2", "testUserId2", 1);


        lab.addStar("testStarId","testUserId", 1);
        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().size()).isEqualTo(2);
    }

    @Test
    public void 연구실_별점_업데이트(){
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

        lab.updateStar("testUserId",10);

        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().get(0).getStarCount()).isEqualTo(10);
    }

    @Test
    public void 연구실_별점_삭제(){
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

        lab.removeStar("testUserId");
        assertThat(lab.getLabId()).isEqualTo("testId");
        assertThat(lab.getSchool()).isEqualTo("DGIST");
        assertThat(lab.getMajor()).isEqualTo("EE");
        assertThat(lab.getProfessor()).isEqualTo("prof");
        assertThat(lab.getContents()).isEqualTo("testContents");
        assertThat(lab.getCreatedAt()).isEqualTo(10000);
        assertThat(lab.getStars().size()).isEqualTo(0);
    }
}
