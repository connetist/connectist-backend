package org.example.boardservice.board.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.domain.Board;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<LikeEntity> likeEntityList;

    public static BoardEntity from(Board board){

        BoardEntity boardEntity = new BoardEntity();
        boardEntity.id = board.getId();
        boardEntity.userId = board.getUserId();
        boardEntity.labId = board.getLabId();
        boardEntity.contents = board.getContents();
        boardEntity.deleted = board.isDeleted();
        boardEntity.createdAt = board.getCreatedAt();
        boardEntity.deletedAt = board.getDeletedAt();
        boardEntity.likeEntityList = board.getLikeList().stream().map(
                        like -> LikeEntity.ofBoardEntity(like, boardEntity)).collect(Collectors.toList());
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
                .likeList(likeEntityList.stream()
                        .map(LikeEntity::toModel)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
