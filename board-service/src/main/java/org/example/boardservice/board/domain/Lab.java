package org.example.boardservice.board.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.boardservice.board.infrastructure.entity.StarEntity;
import org.example.boardservice.error.GlobalException;
import org.example.boardservice.error.ResultCode;

import java.util.List;


@Getter
public class Lab {
    private String labId;
    private String school;
    private String major;
    private String professor;
    private String contents;
    private long createdAt;

    private List<Star> stars;


    @Builder
    public Lab(String labId, String school, String major, String professor, String contents,long createdAt, List<Star> stars) {
        this.labId = labId;
        this.school = school;
        this.major = major;
        this.professor = professor;
        this.contents = contents;
        this.createdAt = createdAt;
        this.stars = stars;
    }

    public void addStar(String userId, String labId, int starCount){

        //유저가 이미 했으면 별점 주기 불가능
        for (Star star : stars){
            if (star.getUserId().equals(userId)){
                throw new GlobalException(ResultCode.USER_STAR_ALREADY_EXISTS);
            }
        }

        Star star = new Star(userId,labId,starCount);
        stars.add(star);
    }

    public void removeStar(String userId){
        if (!stars.removeIf(star->star.getUserId().equals(userId))){
            throw new GlobalException(ResultCode.USER_STAR_NOT_FOUND);
        };
    }

    public void updateStar(String userId, int starCount){
        for (Star star : stars){
            if (star.getUserId().equals(userId)){
                star.updateStarCount(starCount);
            }
        }


    }







}
