package com.example.alarmservice.alarm.service;

import com.example.alarmservice.alarm.controller.port.FcmSender;
import com.example.alarmservice.alarm.domain.Alarm;
import com.example.alarmservice.alarm.domain.value.ReceiverInfo;
import com.example.alarmservice.alarm.dto.firebase.FcmDto;
import com.example.alarmservice.alarm.dto.request.AlarmRequest;
import com.example.alarmservice.alarm.infrastrucutre.TokenRepository;
import com.example.alarmservice.util.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    private final ClockHolder clockHolder;
    private final FcmSender fcmSender;
    private final TokenRepository tokenRepository;

    @Override
    public Alarm makeAlarm(AlarmRequest alarmRequest) {
        String id = alarmRequest.getReceiverInfoRequest().getId();
        String token = tokenRepository.findTokenById(id);
        ReceiverInfo receiverInfo = ReceiverInfo.receiverInfoBuilderAfterRequest(alarmRequest, token);
        return Alarm.convertAlarmRequestToAlarm(alarmRequest, receiverInfo, clockHolder);
    }

    @Override
    public Alarm sendAlarm(Alarm alarm) {
        FcmDto fcmDto = alarm.convertAlarmToFcmDto();
        fcmSender.sendMessage(fcmDto);
        return alarm;
    }

    @Override
    public List<String> findAllTokens() {
        return List.of();
    }
}
