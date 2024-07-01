package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Like;
import org.example.boardservice.board.domain.enuminfo.Major;
import org.example.boardservice.board.domain.enuminfo.School;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "labs")
public class LabEntity {
    @Id
    @Column(name = "lab_id")
    private String id;
    private School school;
    private Major major;
    private String professor;
    private String contents;
    private int likeSum;
    @OneToMany(mappedBy = "labEntity", cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntityList;
    @OneToMany(mappedBy = "labEntity", cascade = CascadeType.ALL)
    private List<StarEntity> starEntityList;
    private long createdAt;

    public Lab toModel() {
        List<Board> boards = new ArrayList<>();
        if(!boardEntityList.isEmpty()){
            boardEntityList.forEach(
                    item -> boards.add(item.toModel())
            );
        }
        return Lab.builder()
                .labId(id)
                .shcool(school)
                .major(major)
                .professor(professor)
                .contents(contents)
                .likeSum(likeSum)
                .boards(boards)
                .likes()
                .createdAt(createdAt);

    }

}
