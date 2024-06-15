package com.example.userservice.user.domain.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Token {

    @Id
    @JsonIgnore
    private String id;

    private String refreshToken;

    private Integer expiration;
}
