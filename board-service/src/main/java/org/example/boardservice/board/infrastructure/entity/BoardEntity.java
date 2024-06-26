package org.example.boardservice.board.infrastructure.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Like;

import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@Setter
public class BoardEntity {
    @Id
    @Column(name = "board_id")
    private String id;
    private String userId;
    private String labId;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;

    public static BoardEntity from(Board board){

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.id = board.getId();
        boardEntity.userId = board.getUserId();
        boardEntity.labId = board.getLabId();
        boardEntity.contents = board.getContents();
        boardEntity.deleted = board.isDeleted();
        boardEntity.createdAt = board.getDeletedAt();
        boardEntity.deletedAt = board.getDeletedAt();
        return boardEntity;
    }

    public Board toModel(){
        return Board.builder()
                .id(id)
                .userId(userId)
                .labId(labId)
                .contents(contents)
                .deleted(deleted)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }

}
