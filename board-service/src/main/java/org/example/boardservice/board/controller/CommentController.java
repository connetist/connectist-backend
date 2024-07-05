package org.example.boardservice.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.dto.request.comment.CommentDeleteRequest;
import org.example.boardservice.board.dto.request.comment.CommentLikeRequest;
import org.example.boardservice.board.dto.request.comment.CommentRequest;
import org.example.boardservice.board.dto.response.BoardResponse;
import org.example.boardservice.board.dto.response.RestResponse;
import org.example.boardservice.board.service.CommentService;
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
    public ResponseEntity<RestResponse<BoardResponse>> createComment(
            @RequestBody CommentRequest commentRequest
    ) {
        BoardResponse boardResponse = commentService.createComment(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.success(boardResponse));
    }

    // 댓글 삭제
    @DeleteMapping("/comment")
    public ResponseEntity<RestResponse<BoardResponse>> deleteComment(
            @RequestBody CommentDeleteRequest commentDeleteRequest
    ) {
        BoardResponse boardResponse = commentService.deleteComment(commentDeleteRequest.getCommentId());
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(boardResponse));
    }

    // 대댓글 생성
    @PostMapping("/comment/re")
    public ResponseEntity<RestResponse<BoardResponse>> createRecomment(
            @RequestBody CommentRequest commentRequest
    ) {
        BoardResponse boardResponse = commentService.createRecomment(commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.success(boardResponse));
    }

    // 대댓글 삭제
    @DeleteMapping("/comment/re")
    public ResponseEntity<RestResponse<BoardResponse>> deleteRecomment(
            @RequestBody CommentDeleteRequest commentDeleteRequest
    ) {
        BoardResponse boardResponse = commentService.deleteRecomment(commentDeleteRequest.getCommentId(), commentDeleteRequest.getRecommentId());
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(boardResponse));
    }


    // 댓글 좋아요 누르기
    @PostMapping("/comment/like/add")
    public ResponseEntity<RestResponse<BoardResponse>> addLikeComment(
            @RequestBody CommentLikeRequest commentLikeRequest
    ) {
        BoardResponse boardResponse = commentService.addLikeComment(commentLikeRequest.getUserId(), commentLikeRequest.getCommentId());
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(boardResponse));
    }

    //  댓글 좋아요 지우기
    @PostMapping("/comment/like/remove")
    public ResponseEntity<RestResponse<BoardResponse>> removeLikeComment(
            @RequestBody CommentLikeRequest commentLikeRequest
    ) {
        BoardResponse boardResponse = commentService.deleteLikeComment(commentLikeRequest.getUserId(), commentLikeRequest.getCommentId());
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(boardResponse));
    }
}
