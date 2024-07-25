package org.example.boardservice.board.dto.request.board;

import lombok.*;

@Getter
@NoArgsConstructor
public class BoardLikeRequest {
    private String userId;

    @Builder
    public BoardLikeRequest(String userId) {
        this.userId = userId;
    }
}
