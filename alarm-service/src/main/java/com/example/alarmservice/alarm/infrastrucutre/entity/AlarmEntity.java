package com.example.alarmservice.alarm.infrastrucutre.entity;

import com.example.alarmservice.alarm.domain.Alarm;
import com.example.alarmservice.alarm.domain.enumeration.AlarmType;
import com.example.alarmservice.alarm.domain.value.AlarmInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collation = "alarm")
public class AlarmEntity {

    private String title;
    private String content;
    private AlarmType alarmType;
    private Long createdAt;

    public static AlarmEntity from(Alarm alarm){
        AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.setCreatedAt(alarm.getCreatedAt());
        alarmEntity.setAlarmType(alarm.getAlarmInfo().getAlarmType());
        alarmEntity.setTitle(alarm.getAlarmInfo().getTitle());
        alarmEntity.setContent(alarm.getAlarmInfo().getContent());
        return alarmEntity;
    }

    public Alarm toModel() {
        AlarmInfo alarmInfo = AlarmInfo.builder()
                .title(title)
                .content(content)
                .alarmType(alarmType)
                .build();
        return Alarm.builder()
                .alarmInfo(alarmInfo)
                .createdAt(createdAt)
                .build();
    }

}
