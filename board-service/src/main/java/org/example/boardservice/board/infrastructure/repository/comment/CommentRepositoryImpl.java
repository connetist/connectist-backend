package org.example.boardservice.board.infrastructure.repository.comment;


import lombok.RequiredArgsConstructor;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.infrastructure.entity.CommentEntity;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentReposotiry {
    private final CommentJpaRepository commentJpaRepository;


    @Transactional
    @Override
    public List<Comment> findByBoardId(String boardId){
        return commentJpaRepository.findAllByBoardId(boardId).stream().map(CommentEntity::toModel).toList();
    }

    @Override
    public Comment findById(String id) {
        CommentEntity commentEntity = commentJpaRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ResultCode.COMMENT_NOT_FOUND));
        return commentEntity.toModel();
    }

    @Override
    public List<Comment> saveComment(Comment comment) {
        commentJpaRepository.save(CommentEntity.from(comment));
        List<CommentEntity> allByBoardId = commentJpaRepository.findAllByBoardId(comment.getBoardId());
        return allByBoardId.stream().map(CommentEntity::toModel).toList();
    }


}
