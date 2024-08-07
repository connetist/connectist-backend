package org.example.boardservice.board.infrastructure.repository.comment;


import lombok.RequiredArgsConstructor;
import org.example.boardservice.board.domain.Comment;
import org.example.boardservice.board.domain.Recomment;
import org.example.boardservice.board.infrastructure.entity.CommentEntity;
import org.example.boardservice.board.infrastructure.entity.RecommentEntity;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentReposotiry {
    private final CommentJpaRepository commentJpaRepository;


    @Transactional
    @Override
    public List<Comment> findByBoardId(String boardId) {
        Specification<CommentEntity> spec = Specification
                .where(CommentSpecs.byBoardId(boardId))
                .and(CommentSpecs.exceptDeleted());

        List<Comment> comments = commentJpaRepository.findAll(spec).stream().map(CommentEntity::toModel).toList();
        comments.forEach(Comment::removeDeletedRecomment);
        return comments;
    }

    @Override
    public Comment findById(String id) {

        Specification<CommentEntity> spec =
                Specification.where(CommentSpecs.byCommentId(id))
                        .and(CommentSpecs.exceptDeleted());

        Comment comment = commentJpaRepository.findAll(spec).stream().findAny()
                .orElseThrow(() -> new GlobalException(ResultCode.COMMENT_NOT_FOUND)).toModel();
        comment.removeDeletedRecomment();
        return comment;
    }

    @Override
    public Comment saveComment(Comment comment) {
        Comment savedComment = commentJpaRepository.save(CommentEntity.from(comment)).toModel();
        savedComment.removeDeletedRecomment();
        return savedComment;
    }

    @Override
    public Recomment findRecommentById(String commentId, String recommentId) {
        List<RecommentEntity> recommentEntityList = commentJpaRepository.findCommentEntityById(commentId).getRecommentEntityList();
        for (RecommentEntity recommentEntity : recommentEntityList) {
            if(recommentEntity.getId().equals(recommentId)){
                return recommentEntity.toModel();
            }
        }
        throw new GlobalException(ResultCode.COMMENT_NOT_FOUND);
    }
}
