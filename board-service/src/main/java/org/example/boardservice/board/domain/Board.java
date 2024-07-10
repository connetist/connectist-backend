package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;
import org.example.boardservice.utils.clock.ClockHolder;
import org.example.boardservice.utils.uuid.UuidHolder;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Slf4j
public class Board {

    private final String id;
    private final String labId;
    private final String userId;
    private final String contents;
    private boolean deleted;
    private final long createdAt;
    private long deletedAt;
    private List<Like> likeList;


    @Builder
    public Board(String id, String labId, String userId, String contents, boolean deleted, long createdAt, long deletedAt, List<Like> likeList) {
        this.id = id;
        this.labId = labId;
        this.userId = userId;
        this.contents = contents;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.likeList = likeList;
    }

    public static Board createBoard(String labId, String userId, String contents,UuidHolder uuidHolder, ClockHolder clockHolder){

        List<Like> likes = new ArrayList<>();

        return Board.builder()
                .id(uuidHolder.random())
                .labId(labId)
                .userId(userId)
                .contents(contents)
                .deleted(false)
                .createdAt(clockHolder.mills())
                .deletedAt(0)
                .likeList(likes)
                .build();
    }

    public void addLike(String userId, String postId, UuidHolder uuidHolder, ClockHolder clockHolder) {
        log.info(this.getLikeList().iterator().toString());
        for(Like like : likeList) {
            if (like.getUserId().equals(userId)) {
                throw new GlobalException(ResultCode.USER_STAR_ALREADY_EXISTS);
            }
        }
        Like like = Like.ofBoard(postId, userId, uuidHolder, clockHolder);
        likeList.add(like);
    }

    public void removeLike(String userId) {
        if (!likeList.removeIf(like -> like.getUserId().equals(userId))) {
            throw new GlobalException(ResultCode.USER_STAR_NOT_FOUND);
        }
    }

    public void deletePost(ClockHolder clockHolder) {
        this.deletedAt = clockHolder.mills();
        this.deleted = true;
    }


}
