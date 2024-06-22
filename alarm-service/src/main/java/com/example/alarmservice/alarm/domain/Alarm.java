package com.example.alarmservice.alarm.domain;

import com.example.alarmservice.alarm.domain.value.AlarmInfo;
import com.example.alarmservice.alarm.dto.firebase.FcmDto;
import com.example.alarmservice.alarm.dto.firebase.FcmSendDto;
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

    public
}
