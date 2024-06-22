package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;

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


}
