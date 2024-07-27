package org.example.boardservice.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.dto.request.board.BoardDeleteRequest;
import org.example.boardservice.board.dto.request.board.BoardLikeRequest;
import org.example.boardservice.board.dto.request.board.BoardRequest;
import org.example.boardservice.board.dto.response.BoardResponse;
import org.example.boardservice.board.dto.response.RestResponse;
import org.example.boardservice.board.service.board.BoardService;
import org.example.boardservice.board.service.comment.CommentService;
import org.example.boardservice.utils.PortListener;
import org.example.boardservice.utils.requestverify.RequestCheck;
import org.hibernate.cfg.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final PortListener portListener;


    // health check
    @GetMapping("/health")
    public ResponseEntity<RestResponse<String>> healthCheck() {
        String port = "Board service : " + portListener.getPort() + "에서 실행중입니다.";
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(port));
    }

    // 특정 연구실 게시글 전체 조회
    @GetMapping("/posts/lab/{labId}")
    public ResponseEntity<RestResponse<List<Board>>> getCertainLabAllPosts(
            @PathVariable String labId
    ) {
        new RequestCheck(labId).checkString();

        List<Board> allByLabId = boardService.getAllByLabId(labId);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(allByLabId));

    }

    // 연구실 특정 게시글 조회 (댓글, 대댓글, 게시글 동시에 보여주기)
    @GetMapping("/post/{postId}")
    public ResponseEntity<RestResponse<BoardResponse>> getCertainLabPost(
            @PathVariable String postId
    ) {
        new RequestCheck(postId).checkString();

        Board board  = boardService.getBoardById(postId);
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        BoardResponse boardResponse = new BoardResponse(board,commentList);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(boardResponse));
    }

    // 게시글 생성
    @PostMapping("/post")
    public ResponseEntity<RestResponse<Board>> createPost(
            @RequestBody BoardRequest boardRequest
    ) {
        new RequestCheck(boardRequest).check();

        Board board = boardService.createBoard(boardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestResponse.success(board)
        );
    }

    // 게시글 삭제
    @DeleteMapping("/post")
    public ResponseEntity<RestResponse<Board>> deletePost(
            @RequestBody BoardDeleteRequest boardDeleteRequest
    ) {
        new RequestCheck(boardDeleteRequest).check();

        Board board = boardService.deleteBoard(boardDeleteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(board));
    }

    // 게시글을 좋아요 누르기
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<RestResponse<Board>> addLikePost(
            @PathVariable String postId,
            @RequestBody BoardLikeRequest boardLikeRequest
            ) {
        new RequestCheck(boardLikeRequest).check();

        Board board = boardService.addLikeBoard(postId, boardLikeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(board));
    }

    // 게시글 좋아요 지우기
    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<RestResponse<Board>> deleteLikePost(
            @PathVariable String postId,
            @RequestBody BoardLikeRequest boardLikeRequest
    ) {
        Board board = boardService.removeLikeBoard(postId, boardLikeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(board));
    }

}
