package org.example.boardservice.board.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.boardservice.board.domain.enuminfo.Major;
import org.example.boardservice.board.domain.enuminfo.School;

import java.util.List;

@Getter
public class Lab {
    private final String labId;
    private final School shcool;
    private final Major major;
    private final String professor;
    private final String contents;
    private final int likeSum;
    private final List<Board> boards;
    private final List<Like> likes;
    private final long createdAt;

    @Builder
    public Lab(String labId, School shcool, Major major, String professor, String contents, int likeSum, List<Board> boards, List<Like> likes, long createdAt) {
        this.labId = labId;
        this.shcool = shcool;
        this.major = major;
        this.professor = professor;
        this.contents = contents;
        this.likeSum = likeSum;
        this.boards = boards;
        this.likes = likes;
        this.createdAt = createdAt;
    }
}
