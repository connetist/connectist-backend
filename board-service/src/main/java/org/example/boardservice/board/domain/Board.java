package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;

import javax.annotation.processing.Generated;
import java.time.Clock;
import java.util.List;

@Getter
public class Board {

    private final String id;
    private final String labId;
    private final String userId;
    private final String contents;
    private final boolean deleted;
    private final long createdAt;
    private final long deletedAt;

    @Builder
    public Board(String id, String labId, String userId, String contents, boolean deleted, long createdAt, long deletedAt) {
        this.id = id;
        this.labId = labId;
        this.userId = userId;
        this.contents = contents;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    static Board CreateBoard(String labId, String userId, String contents, boolean deleted, long deletedAt, UuidHolder uuidHolder, ClockHolder clockHolder){

        return Board.builder()
                .id(uuidHolder.random())
                .labId(labId)
                .userId(userId)
                .contents(contents)
                .deleted(deleted)
                .createdAt(clockHolder.mills())
                .deletedAt(deletedAt)
                .build();
    }

//    static Board DeleteBoard(Board board, ClockHolder clockHolder){
//        return Board.builder()
//                .id(board.id)
//
//
//    }




}
