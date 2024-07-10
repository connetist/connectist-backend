package com.example.alarmservice.alarm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenRequest {
    public String token;
    public String id;
}
