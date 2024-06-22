package com.example.alarmservice.alarm.domain.value;

import com.example.alarmservice.alarm.domain.enumeration.AlarmType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AlarmInfo {
    private final ReceiverInfo receiverInfo;
    private final String title;
    private final String content;
    private final AlarmType alarmType;

    @Builder
    public AlarmInfo(ReceiverInfo receiverInfo, String title, String content, AlarmType alarmType) {
        this.receiverInfo = receiverInfo;
        this.title = title;
        this.content = content;
        this.alarmType = alarmType;
    }
}
