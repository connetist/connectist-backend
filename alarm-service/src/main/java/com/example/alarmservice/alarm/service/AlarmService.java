package com.example.alarmservice.alarm.service;

import com.example.alarmservice.alarm.domain.Alarm;
import com.example.alarmservice.alarm.domain.value.ReceiverInfo;
import com.example.alarmservice.alarm.domain.value.Token;
import com.example.alarmservice.alarm.dto.request.AlarmRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlarmService {

    Alarm makeAlarm(AlarmRequest alarmRequest);

    Alarm sendAlarm(Alarm alarm);

    Alarm makeAndSendAlarm(AlarmRequest alarmRequest);

    List<Token> findAllTokens();

    Token saveToken(Token token);

    Token findToken(String id);
}
