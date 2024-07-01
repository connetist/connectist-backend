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
    private List<Star> starList;


    @Builder
    public Board(String id, String labId, String userId, String contents, boolean deleted, long createdAt, long deletedAt, List<Star> starList) {
        this.id = id;
        this.labId = labId;
        this.userId = userId;
        this.contents = contents;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.starList = starList;
    }

    public static Board CreateBoard(String labId, String userId, String contents,UuidHolder uuidHolder, ClockHolder clockHolder){

        return Board.builder()
                .id(uuidHolder.random())
                .labId(labId)
                .userId(userId)
                .contents(contents)
                .deleted(false)
                .createdAt(clockHolder.mills())
                .deletedAt(clockHolder.mills())
                .build();
    }





}
