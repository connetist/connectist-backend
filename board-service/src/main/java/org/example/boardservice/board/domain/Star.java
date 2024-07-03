package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class Star {
    private String id;
    private String userId;
    private int starCount;



    @Builder
    public Star(String id, String userId, int starCount) {
        this.id = id;
        this.userId = userId;
        this.starCount = starCount;
    }

    public void updateStarCount(int newStarCount) {
        this.starCount = newStarCount;
    }








}
