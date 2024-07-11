package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.dto.request.comment.create.CommentRequest;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Slf4j
public class Comment {
    private String id;
    private String boardId;
    private String userId;
    private String contents;
    private List<Recomment> recomments;
    private List<Like> likes;
    private boolean deleted;
    private long createdAt;
    private long deletedAt;


    @Builder
    public Comment(String id, String boardId, String userId, String contents, boolean deleted, long createdAt, long deletedAt, List<Recomment> recomments, List<Like> likes) {
        this.id = id;
        this.boardId = boardId;
        this.userId = userId;
        this.contents = contents;
        this.recomments =recomments;
        this.likes = likes;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static Comment create(CommentRequest commentRequest, UuidHolder uuidHolder, ClockHolder clockHolder) {
        List<Recomment> recommentList = new ArrayList<>();
        List<Like> likeList = new ArrayList<>();

        return Comment.builder()
                .id(uuidHolder.random())
                .boardId(commentRequest.getBoardId())
                .userId(commentRequest.getUserId())
                .contents(commentRequest.getContents())
                .deleted(false)
                .likes(likeList)
                .recomments(recommentList)
                .createdAt(clockHolder.mills())
                .deletedAt(0)
                .build();
    }

    public void deleteComment(ClockHolder clockHolder, String inputUserId) {
        if (!inputUserId.equals(this.getUserId())) {
            throw new GlobalException(ResultCode.UNAUTHROIZED);
        }
        this.deleted = true;
        this.deletedAt = clockHolder.mills();
    }

    public void addLike(String userId, String commentId, UuidHolder uuidHolder, ClockHolder clockHolder) {

        for(Like like : likes) {
            if(like.getUserId().equals(userId)){
                throw new GlobalException(ResultCode.USER_STAR_ALREADY_EXISTS);
            }
        }
        Like like = Like.ofComment(commentId, userId, uuidHolder, clockHolder);
        log.info(like.toString());
        likes.add(like);
    }

    public void removeLike(String userId) {
        if (!likes.removeIf(like -> like.getUserId().equals(userId))) {
            throw new GlobalException(ResultCode.USER_STAR_NOT_FOUND);
        }
    }

    public void addRecomment(Recomment recomment) {
        recomments.add(recomment);
    }

    public void deleteRecomment(String recommentId, ClockHolder clockHolder) {
        for(Recomment recomment : recomments) {
            if (recomment.getId().equals(recommentId)) {
                if(!recomment.getUserId().equals(userId)) {
                    throw new GlobalException(ResultCode.UNAUTHROIZED);
                }
                recomment.deleteRecomment(clockHolder);
                return;
            }
        }
        throw new GlobalException(ResultCode.COMMENT_NOT_FOUND);
    }

    public void removeDeletedRecomment() {
        this.recomments.removeIf(Recomment::isDeleted);
    }

}
