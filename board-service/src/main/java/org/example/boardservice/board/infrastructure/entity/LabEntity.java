package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.hibernate.annotations.BatchSize;

import javax.swing.undo.StateEdit;
import java.util.List;
import java.util.stream.Collectors;


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
    private long createdAt;

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 1000)
    private List<StarEntity> starEntityList;

    public static LabEntity from(Lab lab){
        LabEntity labEntity = new LabEntity();
        labEntity.id = lab.getLabId();
        labEntity.school = lab.getSchool();
        labEntity.major = lab.getMajor();
        labEntity.professor = lab.getProfessor();
        labEntity.contents = lab.getContents();
        labEntity.createdAt = lab.getCreatedAt();
        labEntity.starEntityList = lab.getStars().stream().map(
                star -> StarEntity.from(star,labEntity))
                .collect(Collectors.toList());
        return labEntity;

    }

    public Lab toModel(){
        Lab lab = Lab.builder()
                .labId(id)
                .school(school)
                .major(major)
                .professor(professor)
                .contents(contents)
                .createdAt(createdAt)
                .stars(starEntityList.stream()
                        .map(StarEntity::toModel)
                        .collect(Collectors.toList()))
                .build();
        return lab;
    }


}
