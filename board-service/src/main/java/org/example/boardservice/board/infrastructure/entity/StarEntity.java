package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.example.boardservice.board.domain.Star;

@Data
@Entity
@Table(name = "stars")
public class StarEntity {
    @Id
    @Column(name = "star_id")
    private String id;
    private String userId;
    @ManyToOne
    @JoinColumn(name = "lab_id", nullable = true)
    private LabEntity labEntity;


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
