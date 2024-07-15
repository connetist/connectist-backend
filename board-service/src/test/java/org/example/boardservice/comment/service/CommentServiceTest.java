package org.example.boardservice.comment.service;

import lombok.Builder;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.board.service.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;

public class CommentServiceTest {
    private CommentServiceImpl commentService;
    private CommentReposotiry commentReposotiry;

    @BeforeEach
    public void init(){

    }
}
