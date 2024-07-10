package org.example.boardservice.mock;

import lombok.Builder;
import org.example.boardservice.board.controller.LabController;
import org.example.boardservice.board.infrastructure.repository.lab.LabRepository;
import org.example.boardservice.board.service.board.BoardService;
import org.example.boardservice.board.service.lab.LabService;
import org.example.boardservice.board.service.lab.LabServiceImpl;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;

public class TestContainer {


    public final LabRepository labRepository;
//    public final BoardRepository boardRepository;
//    public final CommentReposotiry commentReposotiry;
    //
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
                .uuidHolder(uuidHolder)
                .build();

        this.labController = LabController.builder()
                .labService(labService)
                .build();
    }


}
