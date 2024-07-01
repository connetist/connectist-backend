package com.example.alarmservice.alarm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceiverInfoRequest {

    private String id;
    private String email;
    private String nickname;
    private String school;
}
