package org.example.boardservice.board.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.example.boardservice.board.dto.request.comment.create.CommentRequest;
import org.example.boardservice.board.dto.request.comment.create.RecommentRequest;
import org.example.boardservice.board.dto.request.comment.delete.CommentDeleteRequest;
import org.example.boardservice.board.dto.request.comment.delete.RecommentDeleteRequest;
import org.example.boardservice.board.dto.request.comment.like.CommentLikeRequest;
import org.example.boardservice.board.infrastructure.repository.board.BoardRepository;
import org.example.boardservice.board.infrastructure.repository.comment.CommentReposotiry;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentReposotiry commentReposotiry;
    private final BoardRepository boardRepository;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    @Override
    public Comment createComment(CommentRequest commentRequest) {
        Comment comment = Comment.create(commentRequest,uuidHolder, clockHolder);
        return commentReposotiry.saveComment(comment);
    }

    @Override
    public Comment deleteComment(CommentDeleteRequest commentDeleteRequest) {

        Comment comment = commentReposotiry.findById(commentDeleteRequest.getCommentId());
//        if (commentDeleteRequest.getUserId().equals(comment.getUserId())){
//            comment.deleteComment(clockHolder);
//        }else{
//            throw new GlobalException(ResultCode.UNAUTHROIZED);
//        }

        comment.deleteComment(clockHolder, commentDeleteRequest.getUserId());
        return commentReposotiry.saveComment(comment);
    }

    @Override
    public Recomment createRecomment(RecommentRequest recommentRequest) {
        Recomment recomment = Recomment.createRecomment(recommentRequest, uuidHolder, clockHolder);
        Comment comment = commentReposotiry.findById(recommentRequest.getCommentId());
        comment.addRecomment(recomment);

        commentReposotiry.saveComment(comment);
        return commentReposotiry.findRecommentById(comment.getId(), recomment.getId());
    }

    @Override
    public Recomment deleteRecomment(RecommentDeleteRequest recommentDeleteRequest) {
        Comment comment = commentReposotiry.findById(recommentDeleteRequest.getRecommentId());
        comment.deleteRecomment(recommentDeleteRequest.getRecommentId(), clockHolder);

        commentReposotiry.saveComment(comment);
        return commentReposotiry.findRecommentById(comment.getId(), recommentDeleteRequest.getRecommentId());
    }

    @Override
    public Comment addLikeComment(CommentLikeRequest commentLikeRequest) {
        Comment comment = commentReposotiry.findById(commentLikeRequest.getCommentId());
        comment.addLike(commentLikeRequest.getUserId(), commentLikeRequest.getCommentId(), uuidHolder, clockHolder);

        return commentReposotiry.saveComment(comment);
    }

    @Override
    public Comment deleteLikeComment(CommentLikeRequest commentLikeRequest) {
        Comment comment = commentReposotiry.findById(commentLikeRequest.getCommentId());
        log.info(comment.toString());
        comment.removeLike(commentLikeRequest.getUserId());

        return commentReposotiry.saveComment(comment);
    }
}
