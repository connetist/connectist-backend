package com.example.alarmservice.alarm.service;

import com.example.alarmservice.alarm.domain.Alarm;
import com.example.alarmservice.alarm.domain.value.ReceiverInfo;
import com.example.alarmservice.alarm.dto.request.AlarmRequest;

import java.util.List;

public interface AlarmService {

    public Alarm makeAlarm(AlarmRequest alarmRequest);

    public Alarm sendAlarm(Alarm alarm);

    public List<String> findAllTokens();

}
