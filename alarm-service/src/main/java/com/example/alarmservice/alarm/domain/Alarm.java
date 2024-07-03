package com.example.alarmservice.alarm.domain;

import com.example.alarmservice.alarm.domain.enumeration.AlarmType;
import com.example.alarmservice.alarm.domain.value.AlarmInfo;
import com.example.alarmservice.alarm.domain.value.ReceiverInfo;
import com.example.alarmservice.alarm.dto.firebase.FcmDto;
import com.example.alarmservice.alarm.dto.request.AlarmRequest;
import com.example.alarmservice.util.ClockHolder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Alarm {

    private final AlarmInfo alarmInfo;
    private final Long createdAt;


    @Builder
    public Alarm(AlarmInfo alarmInfo, Long createdAt) {
        this.alarmInfo = alarmInfo;
        this.createdAt = createdAt;
    }


    public FcmDto convertAlarmToFcmDto() {
        return FcmDto.builder()
                .token(alarmInfo.getReceiverInfo().getToken())
                .title(alarmInfo.getTitle())
                .body(alarmInfo.getContent())
                .build();
    }


    // id , Token 넣어야 합니다.
    public static Alarm of(AlarmRequest request, ReceiverInfo receiverInfo, ClockHolder clockHolder) {

        AlarmInfo alarmInfo = AlarmInfo.builder()
                .receiverInfo(receiverInfo)
                .content(request.getContent())
                .title(request.getTitle())
                .alarmType(AlarmType.valueOf(request.getAlarmType()))
                .build();

        return Alarm.builder()
                .alarmInfo(alarmInfo)
                .createdAt(clockHolder.getNowUnixTime())
                .build();
    }
}
