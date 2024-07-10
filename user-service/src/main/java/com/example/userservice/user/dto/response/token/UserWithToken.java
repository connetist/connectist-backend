package com.example.userservice.user.dto.response.token;

import com.example.userservice.user.domain.user.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class UserWithToken {
    private User user;
    private String access;
    private String refresh;

    public static UserWithToken from(User user, String access, String refresh) {
        return UserWithToken.builder()
                .user(user)
                .access(access)
                .refresh(refresh)
                .build();
    }

}
