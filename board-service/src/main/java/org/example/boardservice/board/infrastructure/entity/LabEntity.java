package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Like;

import java.util.List;


@Entity
@Table(name = "labs")
@Getter
@Setter
public class LabEntity {
    @Id
    @Column(name = "lab_id")
    private String id;
    private String school;
    private String major;
    private String professor;
    private String contents;
    private long likeSum;
    private long createdAt;

    public static LabEntity from(Lab lab){
        LabEntity labEntity = new LabEntity();
        labEntity.id = lab.getLabId();
        labEntity.school = lab.getSchool();
        labEntity.major = lab.getMajor();
        labEntity.professor = lab.getProfessor();
        labEntity.contents = lab.getContents();
        labEntity.likeSum = lab.getLikeSum();
        labEntity.createdAt = lab.getCreatedAt();
        return labEntity;

    }

    public Lab toModel(){
        return Lab.builder()
                .labId(id)
                .school(school)
                .major(major)
                .professor(professor)
                .contents(contents)
                .likeSum(likeSum)
                .createdAt(createdAt)
                .build();
    }


}
