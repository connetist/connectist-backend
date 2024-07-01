package org.example.boardservice.board.service;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.dto.FindBoardResponse;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

import static org.example.boardservice.board.domain.Board.CreateBoard;

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

    @Override
    public List<Board> getAllByLabId(String labId) {
        List<Board> boards = boardRepository.findAllByLabId(labId);
        return boards;
    }

    @Override
    public FindBoardResponse getBoardById(String boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        List<Comment> commentList = commentReposotiry.findByBoardId(boardId);
        FindBoardResponse findBoardResponse = new FindBoardResponse(board,commentList);
        return findBoardResponse;
    }

    @Override
    public Board createBoard(String userId, String labId, String contents){
        Board board = Board.CreateBoard(userId,labId,contents,uuidHolder,clockHolder);
        return board;
    }

    @Override
    public Board deleteBoard(String boardId){
        Board board=  boardRepository.findByBoardId(boardId);
        return board;
    }
}
