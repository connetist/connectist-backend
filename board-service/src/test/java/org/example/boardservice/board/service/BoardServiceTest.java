package org.example.boardservice.board.service;

import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Like;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.board.dto.request.board.BoardDeleteRequest;
import org.example.boardservice.board.dto.request.board.BoardLikeRequest;
import org.example.boardservice.board.dto.request.board.BoardRequest;
import org.example.boardservice.board.dto.response.BoardResponse;
import org.example.boardservice.board.service.board.BoardServiceImpl;

import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.mock.FakeBoardRepository;
import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class BoardServiceTest {
    private BoardServiceImpl boardService;
    private BoardRepository boardRepository;

    @BeforeEach
    void init(){
        boardRepository = new FakeBoardRepository();
        UuidHolder uuidHolder = new TestUuidHolder("testId");
        ClockHolder clockHolder = new TestClockHolder(10000);

        boardService = BoardServiceImpl.builder()
                .boardRepository(boardRepository)
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .build();

        List<Like> likes = new ArrayList<>();
        likes.add(new Like("Like1", "board1",null,"user1",0));

        Board board1 = Board.builder()
                .id("board1")
                .labId("lab1")
                .userId("user1")
                .contents("contents1")
                .deleted(false)
                .createdAt(1000)
                .deletedAt(0)
                .likeList(likes)
                .build();

        Board board2 = Board.builder()
                .id("board2")
                .labId("lab2")
                .userId("user1")
                .contents("contents1")
                .deleted(false)
                .createdAt(1000)
                .deletedAt(0)
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);

    }

    @Test
    public void 연구실_아이디로_게시판_전체_가져오기(){

        List<Board> boards = boardService.getAllByLabId("lab1");

        assertThat(boards.size()).isEqualTo(1);

    }

    @Test
    public void 개별_게시판_조회(){
        Board board = boardService.getBoardById("board1");

        assertThat(board.getId()).isEqualTo("board1");
        assertThat(board.getLabId()).isEqualTo("lab1");
        assertThat(board.getUserId()).isEqualTo("user1");
        assertThat(board.getContents()).isEqualTo("contents1");
        assertThat(board.getCreatedAt()).isEqualTo(1000);
        assertThat(board.getDeletedAt()).isEqualTo(0);
        assertThat(board.getCreatedAt()).isEqualTo(1000);
        assertThat(board.getDeletedAt()).isEqualTo(0);

    }

    @Test
    public void 게시판_생성(){
        BoardRequest rq = BoardRequest.builder()
                .labId("lab1")
                .userId("user1")
                .content("content1")
                .build();

        Board board = boardService.createBoard(rq);
        assertThat(board.getId()).isEqualTo("testId");
        assertThat(board.getLabId()).isEqualTo("lab1");
        assertThat(board.getUserId()).isEqualTo("user1");
        assertThat(board.getContents()).isEqualTo("content1");
        assertThat(board.getCreatedAt()).isEqualTo(10000);
        assertThat(board.getDeletedAt()).isEqualTo(0);

    }

    @Test
    public void 게시판_삭제(){
        BoardDeleteRequest rq = BoardDeleteRequest.builder()
                .boardId("board1")
                .userId("user1")
                .build();
        Board board = boardService.deleteBoard(rq);

        assertThat(board.getId()).isEqualTo("board1");
        assertThat(board.getLabId()).isEqualTo("lab1");
        assertThat(board.getUserId()).isEqualTo("user1");
        assertThat(board.getContents()).isEqualTo("contents1");
        assertThat(board.getCreatedAt()).isEqualTo(1000);
        assertThat(board.getDeletedAt()).isEqualTo(10000);
        assertThat(board.isDeleted()).isEqualTo(true);
    }

    @Test
    public void 게시판_좋아요_추가(){
        BoardLikeRequest rq = BoardLikeRequest.builder()
                .userId("user2")
                .build();

        Board board = boardService.addLikeBoard("board1",rq);
        assertThat(board.getLikeList().size()).isEqualTo(2);

    }

    @Test
    public void 게시판_좋아요_삭제(){
        BoardLikeRequest rq = BoardLikeRequest.builder()
                .userId("user1")
                .build();

        Board board = boardService.removeLikeBoard("board1",rq);

        assertThat(board.getLikeList().size()).isEqualTo(0);
    }


}
