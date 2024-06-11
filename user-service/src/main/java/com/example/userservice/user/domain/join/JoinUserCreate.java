package com.example.userservice.user.domain.join;

import com.example.userservice.user.domain.user.School;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class JoinUserCreate {

    private final String email;
    private final School school;

    @Builder
    public JoinUserCreate(
            @JsonProperty("email") String email,
            @JsonProperty("school") Integer school
    ) {
        this.email = email;
        this.school = userSchoolFinder(school);
    }

    private School userSchoolFinder(Integer school) {
        if (school == 1) {
            return School.KAIST;
        }
        if (school == 2) {
            return School.DGIST;
        }
        if (school == 3) {
            return School.GIST;
        }
        if (school == 4) {
            return School.UNIST;
        }
        if (school == 5) {
            return School.KENTECH;
        }
        throw new NullPointerException("입력값이 유효하지 않습니다.");

    }
}
