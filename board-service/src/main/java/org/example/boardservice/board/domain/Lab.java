package org.example.boardservice.board.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import org.example.boardservice.board.infrastructure.entity.StarEntity;

import java.util.List;


@Getter
public class Lab {
    private String labId;
    private String school;
    private String major;
    private String professor;
    private String contents;
    private long likeSum;
    private long createdAt;

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL)
    private List<StarEntity> stars;


    @Builder
    public Lab(String labId, String school, String major, String professor, String contents, long likeSum, long createdAt) {
        this.labId = labId;
        this.school = school;
        this.major = major;
        this.professor = professor;
        this.contents = contents;
        this.likeSum = likeSum;
        this.createdAt = createdAt;
    }




}
