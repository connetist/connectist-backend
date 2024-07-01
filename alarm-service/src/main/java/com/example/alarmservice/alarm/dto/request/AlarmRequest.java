package com.example.alarmservice.alarm.dto.request;

import com.example.alarmservice.alarm.domain.enumeration.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmRequest {

    private ReceiverInfoRequest receiverInfoRequest;
    private String title;
    private String content;
    private AlarmType alarmType;
}
