package org.example.boardservice.mock;

import org.example.boardservice.board.controller.LabController;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;
import org.example.boardservice.board.service.BoardService;
import org.example.boardservice.board.service.CommentService;
import org.example.boardservice.board.service.LabService;

public class TestContainer {

    private LabController labController;

    private LabRepository labRepository;
    private BoardRepository boardRepository;
    private CommentReposotiry commentReposotiry;

    private LabService labService;
    private BoardService boardService;
    private CommentService commentService;


}
