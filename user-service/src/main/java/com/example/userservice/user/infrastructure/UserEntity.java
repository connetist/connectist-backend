package com.example.userservice.user.infrastructure;

import com.example.userservice.user.domain.User;
import com.example.userservice.user.domain.user.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "user")
public class UserEntity {

    private String id;
    private String pw;
    private String email;
    private School school;
    private UserDegree degree;
    private UserSex sex;
    private UserMajor major;
    private UserStatus status;
    private String nickname;
    private LocalDateTime createdAt;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setPw(user.getPw());
        userEntity.setEmail(user.getEmail());
        userEntity.setDegree(user.getDegree());
        userEntity.setSex(user.getSex());
        userEntity.setMajor(user.getMajor());
        userEntity.setStatus(user.getStatus());
        userEntity.setNickname(user.getNickname());
        userEntity.setCreatedAt(user.getCreatedAt());
        return userEntity;
    }

    public User toModel() {
        return User.builder()
                .id(id)
                .pw(pw)
                .email(email)
                .school(school)
                .degree(degree)
                .major(major)
                .sex(sex)
                .status(status)
                .nickname(nickname)
                .createdAt(createdAt)
                .build();
    }
}
