package org.example.boardservice.board.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequest {
    private String userId;
    private String labId;
    private String content;
}
