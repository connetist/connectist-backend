package org.example.boardservice.board.service;

import org.example.boardservice.board.dto.request.comment.CommentRequest;
import org.example.boardservice.board.dto.response.BoardResponse;

public interface CommentService {

    BoardResponse createComment(CommentRequest commentRequest);

    BoardResponse deleteComment(String commentId, String userId);

    BoardResponse createRecomment(CommentRequest commentRequest);

    BoardResponse deleteRecomment(String commentId, String recommentId);

    BoardResponse addLikeComment(String userId, String commentId);

    BoardResponse deleteLikeComment(String userId, String commentId);
}
