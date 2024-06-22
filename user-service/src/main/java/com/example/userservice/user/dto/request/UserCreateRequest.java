package com.example.userservice.user.dto.request;
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

}
