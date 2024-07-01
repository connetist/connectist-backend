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
//    private String labId;
    private int starCount;
    private String userId;

    @ManyToOne
    @JoinColumn(name= "lab_id")
    private LabEntity lab;

//    public static StarEntity from(Star star){
//        StarEntity starEntity = new StarEntity();
//        starEntity.setId(star.getId());
//        starEntity.setLabId(star.getLabId());
//        starEntity.setUserId(star.getUserId());
//        starEntity.setStarCount(star.getStarCount());
//        return starEntity;
//
//    }
//
//    public Star toModel(){
//        return Star.builder()
//                .id(id)
//                .labId(labId)
//                .starCount(starCount)
//                .userId(userId)
//                .build();
//    }

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
