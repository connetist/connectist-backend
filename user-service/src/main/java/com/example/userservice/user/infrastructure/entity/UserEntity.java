package com.example.userservice.user.infrastructure.entity;

import com.example.userservice.user.domain.user.User;
import com.example.userservice.user.domain.user.value.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private String id;

    @Column(name = "pw")
    private String pw;
    @Column
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private School school;

    @Column(name = "degree")
    @Enumerated(EnumType.STRING)
    private UserDegree degree;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private UserSex sex;

    @Column(name = "major")
    @Enumerated(EnumType.STRING)
    private UserMajor major;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "nickname")
    private String nickname;
    @Column
    private Long createdAt;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setPw(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setSchool(user.getSchool());
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
