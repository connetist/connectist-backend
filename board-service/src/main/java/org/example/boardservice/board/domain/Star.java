package org.example.boardservice.board.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class Star {
    private String id;
    private String userId;
    private int starCount;
    private String labId;

    @Builder
    public Star(String id, String userId, int starCount, String labId) {
        this.id = id;
        this.userId = userId;
        this.starCount = starCount;
        this.labId = labId;
    }



}
