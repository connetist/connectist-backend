package org.example.boardservice.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.dto.request.board.BoardLikeRequest;
import org.example.boardservice.board.dto.request.board.BoardRequest;
import org.example.boardservice.board.dto.response.BoardResponse;
import org.example.boardservice.board.dto.response.RestResponse;
import org.example.boardservice.board.service.BoardService;
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

    // 특정 연구실 게시글 전체 조회
    @GetMapping("/posts/lab/{labId}")
    public ResponseEntity<RestResponse<List<Board>>> getCertainLabAllPosts(
            @PathVariable String labId
    ) {
        List<Board> allByLabId = boardService.getAllByLabId(labId);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(allByLabId));

    }

    // 연구실 특정 게시글 조회 (댓글, 대댓글, 게시글 동시에 보여주기)
    @GetMapping("/posts/{postId}")
    public ResponseEntity<RestResponse<BoardResponse>> getCertainLabPost(
            @PathVariable String postId
    ) {
        BoardResponse boardById = boardService.getBoardById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(boardById));
    }

    // 게시글 생성
    @PostMapping("/posts")
    public ResponseEntity<RestResponse<Board>> createPost(
            @RequestBody BoardRequest boardRequest
    ) {
        Board board = boardService.createBoard(boardRequest.getUserId(), boardRequest.getLabId(), boardRequest.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                RestResponse.success(board)
        );
    }

    // 게시글 삭제
    @DeleteMapping("/post")
    public ResponseEntity<RestResponse<Board>> deletePost(
            @RequestBody String boardId
    ) {
        Board board = boardService.deleteBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(board));
    }

    // 게시글을 좋아요 누르기
    @PostMapping("/posts/{postId}/add")
    public ResponseEntity<RestResponse<Board>> addLikePost(
            @PathVariable String postId,
            @RequestBody BoardLikeRequest boardLikeRequest
            ) {
        log.info(boardLikeRequest.getUserId());
        Board board = boardService.addLikeBoard(postId, boardLikeRequest.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(board));
    }

    // 게시글 좋아요 지우기
    @PostMapping("/posts/{postId}/delete")
    public ResponseEntity<RestResponse<Board>> deleteLikePost(
            @PathVariable String postId,
            @RequestBody BoardLikeRequest boardLikeRequest
    ) {
        Board board = boardService.removeLikeBoard(postId, boardLikeRequest.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(RestResponse.success(board));
    }

}
