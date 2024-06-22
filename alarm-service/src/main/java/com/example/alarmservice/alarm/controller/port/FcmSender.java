package com.example.alarmservice.alarm.controller.port;

import com.example.alarmservice.alarm.dto.firebase.FcmDto;
import com.example.alarmservice.alarm.dto.firebase.FcmSendDto;

public interface FcmSender {
    public void sendMessage(FcmDto fcmDto);
}
