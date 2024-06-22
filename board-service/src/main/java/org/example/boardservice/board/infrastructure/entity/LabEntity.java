package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.example.boardservice.board.domain.Like;

import java.util.List;

@Data
@Entity
@Table(name = "labs")
public class LabEntity {
    @Id
    @Column(name = "lab_id")
    private String id;
//    private enum school;
//    private enum major
    private String professor;
    private String contents;
    private long likeSum;
    @OneToMany(mappedBy = "labEntity", cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntityList;
    @OneToMany(mappedBy = "labEntity", cascade = CascadeType.ALL)
    private List<LikeEntity> likeList;
    private long createdAt;


}
