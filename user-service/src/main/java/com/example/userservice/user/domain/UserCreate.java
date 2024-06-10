package com.example.userservice.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserCreate {

    private final String email;
    private final UserSchool school;

    @Builder
    public UserCreate(
            @JsonProperty("email") String email,
            @JsonProperty("school") Integer school
    ) {
        this.email = email;
        this.school = userSchoolFinder(school);
    }

    private UserSchool userSchoolFinder(Integer school) {
        if (school == 1) {
            return UserSchool.KAIST;
        }
        if (school == 2) {
            return UserSchool.DGIST;
        }
        if (school == 3) {
            return UserSchool.GIST;
        }
        if (school == 4) {
            return UserSchool.UNIST;
        }
        if (school == 5) {
            return UserSchool.KENTECH;
        }
        throw new NullPointerException("입력값이 null 입니다.");

    }
}
