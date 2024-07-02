package com.example.alarmservice.alarm.controller;

import com.example.alarmservice.alarm.domain.Alarm;
import com.example.alarmservice.alarm.domain.value.Token;
import com.example.alarmservice.alarm.dto.request.AlarmRequest;
import com.example.alarmservice.alarm.dto.request.TokenRequest;
import com.example.alarmservice.alarm.dto.response.GlobalResponse;
import com.example.alarmservice.alarm.service.AlarmService;
import com.example.alarmservice.util.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/send")
    public ResponseEntity<GlobalResponse<Alarm>> sendAlarm(
            @RequestBody AlarmRequest alarmRequest
    ) {
        Alarm alarm = alarmService.makeAndSendAlarm(alarmRequest);
        return GlobalResponse.of(SuccessCode.ALARM_SEND_SUCCESS, alarm);
    }

    @PostMapping("/token/save")
    public ResponseEntity<GlobalResponse<Token>> saveToken(
            @RequestBody TokenRequest tokenRequest
    ) {
        Token token = alarmService.saveToken(Token.from(tokenRequest));
        return GlobalResponse.of(SuccessCode.TOKEN_SAVE_SUCCESS, token);
    }

    @GetMapping("/token")
    public ResponseEntity<GlobalResponse<Token>> getToken(
            @RequestBody String id
    ) {
        Token token = alarmService.findToken(id);
        return GlobalResponse.of(SuccessCode.TOKEN_FIND_SUCCESS, token);
    }
}
