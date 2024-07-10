package com.example.alarmservice.alarm.domain.value;

import com.example.alarmservice.alarm.domain.enumeration.School;
import com.example.alarmservice.alarm.dto.request.AlarmRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReceiverInfo {

    private final String id;
    private final String email;
    private final String nickname;
    private final String token;
    private final School school;

    @Builder
    public ReceiverInfo(String id, String email, String nickname, String token, School school) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.token = token;
        this.school = school;
    }

    public static ReceiverInfo of(AlarmRequest alarmRequest, String token) {
        return ReceiverInfo.builder()
                .id(alarmRequest.getReceiverInfoRequest().getId())
                .email(alarmRequest.getReceiverInfoRequest().getEmail())
                .nickname(alarmRequest.getReceiverInfoRequest().getNickname())
                .token(token)
                .school(School.valueOf(alarmRequest.getReceiverInfoRequest().getSchool()))
                .build();
    }
}
