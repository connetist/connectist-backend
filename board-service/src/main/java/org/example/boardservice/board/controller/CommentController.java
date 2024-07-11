package org.example.boardservice.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.example.boardservice.board.dto.request.comment.create.RecommentRequest;
import org.example.boardservice.board.dto.request.comment.delete.CommentDeleteRequest;
import org.example.boardservice.board.dto.request.comment.delete.RecommentDeleteRequest;
import org.example.boardservice.board.dto.request.comment.like.CommentLikeRequest;
import org.example.boardservice.board.dto.request.comment.create.CommentRequest;
import org.example.boardservice.board.dto.response.RestResponse;
import org.example.boardservice.board.service.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comment")
    public ResponseEntity<RestResponse<Comment>> createComment(
            @RequestBody CommentRequest commentRequest
    ) {
        Comment comment = commentService.createComment(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.success(comment));
    }

    // 댓글 삭제
    @DeleteMapping("/comment")
    public ResponseEntity<RestResponse<Comment>> deleteComment(
            @RequestBody CommentDeleteRequest commentDeleteRequest
    ) {
        Comment comment = commentService.deleteComment(commentDeleteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(comment));
    }

    // 대댓글 생성
    @PostMapping("/recomment")
    public ResponseEntity<RestResponse<Recomment>> createRecomment(
            @RequestBody RecommentRequest recommentRequest
    ) {
        Recomment recomment = commentService.createRecomment(recommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.success(recomment));
    }

    // 대댓글 삭제
    @DeleteMapping("/recomment")
    public ResponseEntity<RestResponse<Recomment>> deleteRecomment(
            @RequestBody RecommentDeleteRequest recommentDeleteRequest
    ) {
        Recomment recomment = commentService.deleteRecomment(recommentDeleteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(recomment));
    }


    // 댓글 좋아요 누르기
    @PostMapping("/comment/like")
    public ResponseEntity<RestResponse<Comment>> addLikeComment(
            @RequestBody CommentLikeRequest commentLikeRequest
    ) {
        Comment comment = commentService.addLikeComment(commentLikeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(comment));
    }

    //  댓글 좋아요 지우기
    @DeleteMapping("/comment/like")
    public ResponseEntity<RestResponse<Comment>> removeLikeComment(
            @RequestBody CommentLikeRequest commentLikeRequest
    ) {
        Comment comment = commentService.deleteLikeComment(commentLikeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(comment));
    }
}
