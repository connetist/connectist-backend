package org.example.boardservice.board.domain;

import java.util.List;

public class Comment {
    private String id;
    private String boardId;
    private String userId;
    private String contents;
    private List<Recomment> recomments;
    private List<Like> likes;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;

}
