package com.example.alarmservice.alarm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmRequest {

    private ReceiverInfoRequest receiverInfoRequest;
    private String title;
    private String content;
    private String alarmType;
}
