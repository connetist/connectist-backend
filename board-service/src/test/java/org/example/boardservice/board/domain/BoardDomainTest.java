package org.example.boardservice.board.domain;

import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class BoardDomainTest {
    private UuidHolder uuidHolder = new TestUuidHolder("testId");
    private ClockHolder clockHolder = new TestClockHolder(10000);

    @Test
    public void 게시판_생성(){
        Board board = Board.createBoard("labId","userId","contents",uuidHolder,clockHolder);

        assertThat(board).isNotNull();
        assertThat(board.getId()).isEqualTo("testId");
        assertThat(board.getLabId()).isEqualTo("labId");
        assertThat(board.getUserId()).isEqualTo("userId");
        assertThat(board.getContents()).isEqualTo("contents");
        assertThat(board.getCreatedAt()).isEqualTo(10000);
        assertThat(board.getDeletedAt()).isEqualTo(0);
        assertThat(board.getLikeList().size()).isEqualTo(0);

    }

    @Test
    public void 게시판_삭제(){
        Board board = Board.createBoard("labId","userId","contents",uuidHolder,clockHolder);
        board.deletePost(clockHolder,"userId");

        assertThat(board.isDeleted()).isEqualTo(true);


    }

    @Test
    public void 게시판_좋아요_추가(){
        Board board = Board.createBoard("labId","userId","contents",uuidHolder,clockHolder);
        board.addLike("userId2","testId",uuidHolder,clockHolder);

        assertThat(board.getLikeList().size()).isEqualTo(1);
        assertThat(board.getLikeList().get(0).getId()).isEqualTo("testId");
        assertThat(board.getLikeList().get(0).getUserId()).isEqualTo("userId2");
    }

    @Test
    public void 게시판_좋아요_삭제(){
        Board board = Board.createBoard("labId","userId","contents",uuidHolder,clockHolder);
        board.addLike("userId2","testId",uuidHolder,clockHolder);

        board.removeLike("userId2");

        assertThat(board.getLikeList().size()).isEqualTo(0);
    }



}
