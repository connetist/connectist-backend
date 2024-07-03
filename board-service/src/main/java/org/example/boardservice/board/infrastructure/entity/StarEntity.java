package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import org.example.boardservice.board.domain.Star;

@Getter
@Setter
@Entity
@Table(name = "stars")
public class StarEntity {
    @Id
    @Column(name = "star_id")
    private String id;
    private int starCount;
    private String userId;

    @ManyToOne
    @JoinColumn(name= "lab_id")
    private LabEntity lab;

    public static StarEntity from(Star star, LabEntity labEntity){
        StarEntity starEntity = new StarEntity();
        starEntity.id = star.getId();
        starEntity.starCount = star.getStarCount();
        starEntity.userId = star.getUserId();
        starEntity.lab = labEntity;
        return starEntity;
    }

    public Star toModel(){
        return Star.builder()
                .id(id)
                .starCount(starCount)
                .userId(userId)
                .build();

    }


    public Star toModel() {
        return Star.builder()
                .id(id)
                .userId(userId)
                .labId(labEntity != null ? String.valueOf(labEntity.getId()) : null)
                .build();
    }

    public static StarEntity from(Star star){
        StarEntity starEntity = new StarEntity();
        starEntity.setId(star.getId());
        starEntity.setUserId(star.getUserId());
        if (star.getLabId() != null) {
            LabEntity labEntity = new LabEntity();
            labEntity.setId(String.valueOf(star.getLabId()));
            starEntity.setLabEntity(labEntity);
        }
        return starEntity;
    }
}
