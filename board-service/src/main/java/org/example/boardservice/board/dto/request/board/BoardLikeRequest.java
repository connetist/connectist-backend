package org.example.boardservice.board.dto.request.board;

import lombok.*;

@Getter

public class BoardLikeRequest {
    private String userId;
    private String postId;

    @Builder
    public BoardLikeRequest(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
