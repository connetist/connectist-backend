package org.example.boardservice.comment.service;

import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Like;
import org.example.boardservice.board.dto.request.comment.like.CommentLikeRequest;
import org.example.boardservice.board.infrastructure.repository.comment.CommentRepositoryImpl;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.board.service.comment.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class CommentServiceTest {


    @Autowired
    private CommentRepositoryImpl commentRepositoryImpl;
    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Test
    @DisplayName("100명이 좋아요를 동시에 누르면 좋아요수가 100 증가한다")
    void createCommentLike_success_concurrent() throws InterruptedIOException, InterruptedException {
        Comment comment = Comment.builder()
                .id("commentId")
                .boardId("boardId")
                .userId("userId")
                .contents("contents")
                .deleted(false)
                .likes(new ArrayList<>())
                .recomments(new ArrayList<>())
                .createdAt(10)
                .deletedAt(0)
                .build();

        commentRepositoryImpl.saveComment(comment);

        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try{
                    CommentLikeRequest commentLikeRequest = CommentLikeRequest.builder()
                            .userId(finalI + " userId")
                            .commentId("commentId")
                            .build();
                    commentServiceImpl.addLikeComment(commentLikeRequest);
                }catch (Exception e){

                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        comment = commentRepositoryImpl.findById("commentId");
        assertThat(comment.getLikes().size()).isEqualTo(count);
    }

    @Test
    @DisplayName("100명이 좋아요를 순차적으로 누르면 좋아요수가 100 증가한다")
    void createCommentLike_success() throws InterruptedIOException, InterruptedException {
        Comment comment = Comment.builder()
                .id("commentId")
                .boardId("boardId")
                .userId("userId")
                .contents("contents")
                .deleted(false)
                .likes(new ArrayList<>())
                .recomments(new ArrayList<>())
                .createdAt(10)
                .deletedAt(0)
                .build();

        commentRepositoryImpl.saveComment(comment);

        int count = 100;
        for (int i = 0 ; i < count ; i ++){
            CommentLikeRequest commentLikeRequest = CommentLikeRequest.builder()
                    .userId(i + " userId")
                    .commentId("commentId")
                    .build();
            commentServiceImpl.addLikeComment(commentLikeRequest);
        }
        comment = commentRepositoryImpl.findById("commentId");
        assertThat(comment.getLikes().size()).isEqualTo(count);
    }
}
