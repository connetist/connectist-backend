package com.example.alarmservice.alarm.service;

import com.example.alarmservice.alarm.controller.port.FcmSender;
import com.example.alarmservice.alarm.domain.Alarm;
import com.example.alarmservice.alarm.domain.value.ReceiverInfo;
import com.example.alarmservice.alarm.domain.value.Token;
import com.example.alarmservice.alarm.dto.firebase.FcmDto;
import com.example.alarmservice.alarm.dto.request.AlarmRequest;
import com.example.alarmservice.alarm.infrastrucutre.TokenRepository;
import com.example.alarmservice.util.ClockHolder;
import com.example.alarmservice.util.exception.ErrorCode;
import com.example.alarmservice.util.exception.GlobalException;
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
    public Token findToken(String id) {
        try {
            String tokenById = tokenRepository.findTokenById(id);
            return Token.of(tokenById, id);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.TOKEN_NOTFOUND);
        }
    }

    @Override
    public Alarm makeAlarm(AlarmRequest alarmRequest) {
        String id = alarmRequest.getReceiverInfoRequest().getId();
        Token token = findToken(id);
        ReceiverInfo receiverInfo = ReceiverInfo.of(alarmRequest, token.getTokenString());
        return Alarm.of(alarmRequest, receiverInfo, clockHolder);
    }

    @Override
    public Alarm makeAndSendAlarm(AlarmRequest alarmRequest) {
        Alarm alarm = makeAlarm(alarmRequest);
        return sendAlarm(alarm);
    }

    @Override
    public Alarm sendAlarm(Alarm alarm) {
        FcmDto fcmDto = alarm.convertAlarmToFcmDto();
        try {
            fcmSender.sendMessage(fcmDto);
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.ALARM_SEND_ERROR);
        }
        return alarm;
    }

    @Override
    public List<Token> findAllTokens() {
        return null;
    }

    @Override
    public Token saveToken(Token token) {
        try {
            tokenRepository.saveToken(token.getId(), token.getTokenString());
        } catch (Exception e) {
            throw new GlobalException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return token;
    }
}
