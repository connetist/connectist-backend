package org.example.boardservice.board.service;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.dto.response.BoardResponse;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final CommentReposotiry commentReposotiry;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    public BoardServiceImpl(BoardRepository boardRepository, CommentReposotiry commentReposotiry, UuidHolder uuidHolder, ClockHolder clockHolder) {
        this.boardRepository = boardRepository;
        this.commentReposotiry = commentReposotiry;
        this.uuidHolder = uuidHolder;
        this.clockHolder = clockHolder;
    }

    @Transactional
    @Override
    public List<Board> getAllByLabId(String labId) {
        List<Board> boards = boardRepository.findAllByLabId(labId);
        return boards;
    }

    @Override
    public BoardResponse getBoardById(String boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        List<Comment> commentList = commentReposotiry.findByBoardId(boardId);
        return new BoardResponse(board,commentList);
    }

    @Override
    public Board createBoard(String userId, String labId, String contents){
        Board board = Board.createBoard(userId,labId,contents,uuidHolder,clockHolder);
        return boardRepository.save(board);
    }

    @Override
    public Board deleteBoard(String boardId){
        log.info("Deleting board with id {}", boardId);
        Board board = boardRepository.findByBoardId(boardId);
        board.deletePost(clockHolder);
        log.info(board.toString());
        return boardRepository.save(board);
    }

    @Override
    public Board addLikeBoard(String boardId, String userId) {
        Board board = boardRepository.findByBoardId(boardId);
        board.addLike(userId, boardId, uuidHolder, clockHolder);
        return boardRepository.save(board);
    }

    @Override
    public Board removeLikeBoard(String boardId, String userId) {
        Board board = boardRepository.findByBoardId(boardId);
        board.removeLike(userId);
        return boardRepository.save(board);
    }

}
