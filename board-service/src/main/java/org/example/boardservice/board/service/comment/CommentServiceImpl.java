package org.example.boardservice.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Board;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.example.boardservice.board.dto.request.comment.CommentRequest;
import org.example.boardservice.board.dto.response.BoardResponse;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.example.boardservice.utils.ClockHolder;
import org.example.boardservice.utils.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentReposotiry commentReposotiry;
    private final BoardRepository boardRepository;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    @Override
    public BoardResponse createComment(CommentRequest cr) {
        Comment comment = Comment.create(cr,uuidHolder, clockHolder);

        List<Comment> comments = commentReposotiry.saveComment(comment);

        Board board = boardRepository.findByBoardId(cr.getBoardId());
        return new BoardResponse(board, comments);
    }

    @Override
    public BoardResponse deleteComment(String commentId, String userId) {

        Comment comment = commentReposotiry.findById(commentId);
        if (userId.equals(comment.getUserId())){
            comment.deleteComment(clockHolder);
        }else{
            throw new GlobalException(ResultCode.UNAUTHROIZED);
        }
        List<Comment> comments = commentReposotiry.saveComment(comment);
        Board board = boardRepository.findByBoardId(comment.getBoardId());
        return new BoardResponse(board, comments);
    }

    @Override
    public BoardResponse createRecomment(CommentRequest commentRequest) {
        Recomment recomment = Recomment.createRecomment(commentRequest, uuidHolder, clockHolder);
        Comment comment = commentReposotiry.findById(commentRequest.getCommentId());
        comment.addRecomment(recomment);

        List<Comment> comments = commentReposotiry.saveComment(comment);
        Board board = boardRepository.findByBoardId(comment.getBoardId());
        return new BoardResponse(board, comments);
    }

    @Override
    public BoardResponse deleteRecomment(String commentId, String recommentId) {
        Comment comment = commentReposotiry.findById(commentId);
        comment.deleteRecomment(recommentId, clockHolder);

        List<Comment> comments = commentReposotiry.saveComment(comment);
        Board board = boardRepository.findByBoardId(comment.getBoardId());
        return new BoardResponse(board, comments);
    }

    @Override
    public BoardResponse addLikeComment(String userId, String commentId) {
        Comment comment = commentReposotiry.findById(commentId);
        comment.addLike(userId, commentId, uuidHolder, clockHolder);

        List<Comment> comments = commentReposotiry.saveComment(comment);
        Board board = boardRepository.findByBoardId(comment.getBoardId());
        return new BoardResponse(board, comments);
    }

    @Override
    public BoardResponse deleteLikeComment(String userId, String commentId) {
        Comment comment = commentReposotiry.findById(commentId);
        log.info(comment.toString());
        comment.removeLike(userId);

        List<Comment> comments = commentReposotiry.saveComment(comment);
        Board board = boardRepository.findByBoardId(comment.getBoardId());
        return new BoardResponse(board, comments);
    }
}
