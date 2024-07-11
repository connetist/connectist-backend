package org.example.boardservice.board.service.board;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.dto.request.board.BoardDeleteRequest;
import org.example.boardservice.board.dto.request.board.BoardLikeRequest;
import org.example.boardservice.board.dto.request.board.BoardRequest;
import org.example.boardservice.board.dto.response.BoardResponse;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@Slf4j
public class BoardServiceImpl implements BoardService {
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
    public Board createBoard(BoardRequest boardRequest){
        Board board = Board.createBoard(boardRequest.getLabId(), boardRequest.getUserId(), boardRequest.getContent(), uuidHolder,clockHolder);
        return boardRepository.save(board);
    }

    @Override
    public Board deleteBoard(BoardDeleteRequest boardDeleteRequest){
        Board board = boardRepository.findByBoardId(boardDeleteRequest.getBoardId());
//        if (board.getUserId().equals(userId)){
//            board.deletePost(clockHolder);
//        }else{
//            throw new GlobalException(ResultCode.UNAUTHROIZED);
//        }
        board.deletePost(clockHolder, board.getUserId());
        return boardRepository.save(board);
    }

    @Override
    public Board addLikeBoard(String boardId, BoardLikeRequest boardLikeRequest) {
        Board board = boardRepository.findByBoardId(boardId);
        board.addLike(boardLikeRequest.getUserId(), boardId, uuidHolder, clockHolder);
        return boardRepository.save(board);
    }

    @Override
    public Board removeLikeBoard(String boardId, BoardLikeRequest boardLikeRequest) {
        Board board = boardRepository.findByBoardId(boardId);
        board.removeLike(boardLikeRequest.getUserId());
        return boardRepository.save(board);
    }

}
