package com.example.alarmservice.alarm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmSendRequest {
    private ReceiverInfoRequest receiverInfoRequest;
    private String title;
    private String content;
    private Integer alarmType;
}
