package org.example.boardservice.board.service.comment;

import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.example.boardservice.board.dto.request.comment.create.CommentRequest;
import org.example.boardservice.board.dto.request.comment.create.RecommentRequest;
import org.example.boardservice.board.dto.request.comment.delete.CommentDeleteRequest;
import org.example.boardservice.board.dto.request.comment.delete.RecommentDeleteRequest;
import org.example.boardservice.board.dto.request.comment.like.CommentLikeRequest;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentRequest commentRequest);

    Comment deleteComment(CommentDeleteRequest commentDeleteRequest);

    Recomment createRecomment(RecommentRequest recommentRequest);

    Recomment deleteRecomment(RecommentDeleteRequest recommentDeleteRequest);

    Comment addLikeComment(CommentLikeRequest commentLikeRequest);

    Comment deleteLikeComment(CommentLikeRequest commentLikeRequest);

    List<Comment> getCommentsByPostId(String postId);
}
