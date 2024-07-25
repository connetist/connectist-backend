package com.example.userservice.user.dto.request.user;
import com.example.userservice.user.domain.user.value.UserDegree;
import com.example.userservice.user.domain.user.value.UserMajor;
import com.example.userservice.user.domain.user.value.UserSex;
import com.example.userservice.util.exception.ErrorCode;
import com.example.userservice.util.exception.code.GlobalException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    private final String pw;
    private final String email;
    private final Integer degree;
    private final Integer sex;
    private final Integer major;
    private final String nickname;

    @Builder
    public UserCreateRequest(
            @JsonProperty("email") String email,
            @JsonProperty("pw") String pw,
            @JsonProperty("degree") Integer degree,
            @JsonProperty("sex") Integer sex,
            @JsonProperty("major") Integer major,
            @JsonProperty("nickname") String nickname

    ) {
        this.email = email;
        this.pw = pw;
        this.degree = degree;
        this.sex = sex;
        this.major = major;
        this.nickname = nickname;
    }

    public UserDegree findDegree() {
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
        throw new GlobalException(ErrorCode.INVALID_INPUT);
    }
    public UserSex findUserSex() {
        if (sex == 1) {
            return UserSex.MALE;
        }
        if (sex == 2) {
            return UserSex.FEMALE;
        }
        if (sex == 3) {
            return UserSex.OTHERS;
        }

        throw new GlobalException(ErrorCode.INVALID_INPUT);
    }
    public UserMajor findUserMajor() {
        if (major == 1) {
            return UserMajor.AI;
        }
        if (major == 2) {
            return UserMajor.Biology;
        }
        if (major == 3) {
            return UserMajor.Chemistry;
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

        throw new GlobalException(ErrorCode.INVALID_INPUT);
    }
}
