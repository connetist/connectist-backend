package com.example.userservice.user.domain;

import com.example.userservice.user.domain.join.JoinUser;
import com.example.userservice.user.domain.user.School;
import com.example.userservice.user.domain.user.UserDegree;
import com.example.userservice.user.domain.user.UserMajor;
import com.example.userservice.user.domain.user.UserSex;
import com.example.userservice.util.exception.InputErrorException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    private final String pw;
    private final String email;
    private final School school;
    private final UserDegree degree;
    private final UserSex sex;
    private final UserMajor major;
    private final String nickname;

    @Builder
    public UserCreate(
            String email,
            School school,
            String pw,
            UserDegree degree,
            UserSex sex,
            UserMajor major,
            String nickname

    ) {
        this.email = email;
        this.school = school;
        this.pw = pw;
        this.degree = degree;
        this.sex = sex;
        this.major = major;
        this.nickname = nickname;
    }

    public UserCreate from(JoinUser joinUser,
                           String id,
                           String pw,
                           Integer degree,
                           Integer sex,
                           Integer major,
                           String nickname) {

        return UserCreate.builder()
                .pw(pw)
                .email(joinUser.getEmail())
                .school(joinUser.getSchool())
                .degree(findDegree(degree))
                .sex(findUserSex(sex))
                .major(findUserMajor(major))
                .nickname(nickname)
                .build();
    }

    public UserCreate updatePwAfterEncode(UserCreate userCreate, String newPw) {
        return UserCreate.builder()
                .pw(newPw)
                .email(userCreate.getEmail())
                .school(userCreate.getSchool())
                .degree(userCreate.getDegree())
                .sex(userCreate.getSex())
                .major(userCreate.getMajor())
                .nickname(userCreate.getNickname())
                .build();
    }

    // 아래는 Integer -> Enum Method 입니다.
    private UserDegree findDegree(Integer degree) {
        if (degree == 1) {
            return UserDegree.Bachelor;
        }
        if (degree == 2) {
            return UserDegree.Master;
        }
        if (degree == 3) {
            return UserDegree.Doctor;
        }
        if (degree == 4) {
            return UserDegree.OTHERS;
        }
        throw new InputErrorException(degree);
    }
    private UserSex findUserSex(Integer sex) {
        if (sex == 1) {
            return UserSex.MALE;
        }
        if (sex == 2) {
            return UserSex.FEMALE;
        }
        if (sex == 3) {
            return UserSex.OTHERS;
        }

        throw new InputErrorException(sex);
    }
    private UserMajor findUserMajor(Integer major) {
        if (major == 1) {
            return UserMajor.AI;
        }
        if (major == 2) {
            return UserMajor.Biology;
        }
        if (major == 3) {
            return UserMajor.ChemiStry;
        }
        if (major == 4) {
            return UserMajor.Physics;
        }
        if (major == 5) {
            return UserMajor.ComputerScience;
        }
        if (major == 6) {
            return UserMajor.MaterialScience;
        }

        throw new InputErrorException(major);
    }
}
