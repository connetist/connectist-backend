package org.example.boardservice.board.domain;

import java.util.List;

public class Board {

    private String id;
    private String labId;
    private String userId;
    private List<Comment> comments;
    private List<Like> likes;
    private String contents;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;
}
