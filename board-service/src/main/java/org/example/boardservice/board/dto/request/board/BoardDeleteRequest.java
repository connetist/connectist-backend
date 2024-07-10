package org.example.boardservice.board.dto.request.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardDeleteRequest {
    private String boardId;
    private String userId;
}
