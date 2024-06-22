package com.example.alarmservice.alarm.service;

import com.example.alarmservice.alarm.domain.Alarm;

import java.util.List;

public interface AlarmService {

    public Alarm sendAlarm();

    public List<String> findAllTokens();

}
