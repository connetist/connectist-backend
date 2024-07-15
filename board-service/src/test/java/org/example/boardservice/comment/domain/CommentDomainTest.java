package org.example.boardservice.comment.domain;

import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.dto.request.comment.create.CommentRequest;
import org.example.boardservice.mock.TestClockHolder;
import org.example.boardservice.mock.TestUuidHolder;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class CommentDomainTest {
    private UuidHolder uuidHolder = new TestUuidHolder("testId");
    private ClockHolder clockHolder = new TestClockHolder(10000);

    @Test
    public void 코멘트_빌더(){
        Comment comment = Comment.builder()
                .id("comment1")
                .boardId("board1")
                .userId("user1")
                .contents("contents")
                .recomments(new ArrayList<>())
                .likes(new ArrayList<>())
                .deleted(false)
                .createdAt(10000)
                .deletedAt(0)
                .build();

        assertThat(comment.getId()).isEqualTo("comment1");
        assertThat(comment.getBoardId()).isEqualTo("board1");
        assertThat(comment.getUserId()).isEqualTo("user1");
        assertThat(comment.getContents()).isEqualTo("contents");
        assertThat(comment.getRecomments().size()).isEqualTo(0);
        assertThat(comment.getLikes().size()).isEqualTo(0);
        assertThat(comment.getCreatedAt()).isEqualTo(10000);
        assertThat(comment.getDeletedAt()).isEqualTo(0);
        assertThat(comment.getCreatedAt()).isEqualTo(10000);

    }

    @Test
    public void 코멘트_생성(){

        CommentRequest rq = CommentRequest.builder()
                .boardId("board1")
                .userId("user1")
                .contents("contents")
                .build();

        Comment comment = Comment.create(rq,uuidHolder,clockHolder);
        assertThat(comment.getId()).isEqualTo("testId");
        assertThat(comment.getBoardId()).isEqualTo("board1");
        assertThat(comment.getUserId()).isEqualTo("user1");
        assertThat(comment.getContents()).isEqualTo("contents");
        assertThat(comment.getRecomments().size()).isEqualTo(0);
        assertThat(comment.getLikes().size()).isEqualTo(0);
        assertThat(comment.getCreatedAt()).isEqualTo(10000);
        assertThat(comment.getDeletedAt()).isEqualTo(0);
        assertThat(comment.getCreatedAt()).isEqualTo(10000);

    }

    @Test
    public void 코멘트_삭제(){
        Comment comment = Comment.builder()
                .id("comment1")
                .boardId("board1")
                .userId("user1")
                .contents("contents")
                .recomments(new ArrayList<>())
                .likes(new ArrayList<>())
                .deleted(false)
                .createdAt(10000)
                .deletedAt(0)
                .build();

        comment.deleteComment(clockHolder,"user1");

        assertThat(comment.isDeleted()).isEqualTo(true);

    }

    @Test
    public void 코멘트_좋아요(){
        Comment comment = Comment.builder()
                .id("comment1")
                .boardId("board1")
                .userId("user1")
                .contents("contents")
                .recomments(new ArrayList<>())
                .likes(new ArrayList<>())
                .deleted(false)
                .createdAt(10000)
                .deletedAt(0)
                .build();

        comment.addLike("user2","comment1",uuidHolder,clockHolder);
        assertThat(comment.getLikes().size()).isEqualTo(1);

    }

    @Test
    public void 코멘트_좋아요_삭제(){
        Comment comment = Comment.builder()
                .id("comment1")
                .boardId("board1")
                .userId("user1")
                .contents("contents")
                .recomments(new ArrayList<>())
                .likes(new ArrayList<>())
                .deleted(false)
                .createdAt(10000)
                .deletedAt(0)
                .build();

        comment.addLike("user2","comment1",uuidHolder,clockHolder);
        comment.removeLike("user2");

        assertThat(comment.getLikes().size()).isEqualTo(0);

    }
}
