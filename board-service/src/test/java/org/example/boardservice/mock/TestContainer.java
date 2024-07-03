package org.example.boardservice.mock;

import lombok.Builder;
import org.example.boardservice.board.controller.BoardController;
import org.example.boardservice.board.controller.LabController;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;
import org.example.boardservice.board.service.BoardService;
import org.example.boardservice.board.service.CommentService;
import org.example.boardservice.board.service.LabService;
import org.example.boardservice.board.service.LabServiceImpl;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;

public class TestContainer {


    public final LabRepository labRepository;
//    public final BoardRepository boardRepository;
//    public final CommentReposotiry commentReposotiry;

    public final LabService labService;
//    public final BoardService boardService;
//    public final CommentService commentService;

    public final LabController labController;
//    public final BoardController boardController;

    public final UuidHolder uuidHolder;
    public final ClockHolder clockHolder;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder){
        this.clockHolder = clockHolder;
        this.uuidHolder = uuidHolder;

        this.labRepository = new FakeLabRepository();

        this.labService = LabServiceImpl.builder()
                .labRepository(labRepository)
                .build();

        this.labController = LabController.builder()
                .labService(labService)
                .build();
    }


}
