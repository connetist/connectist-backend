package com.example.alarmservice.alarm.dto.firebase;

import com.example.alarmservice.alarm.controller.port.FcmSender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class FcmDto {
    private String token;

    private String title;

    private String body;

    @Builder
    public FcmDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }

    public FcmSendDto convertFcmDtoToFcmSendDto() {
        return FcmSendDto.builder()
                .validateOnly(false)
                .message(
                        FcmSendDto.Message.builder()
                                .token(token)
                                .notification(
                                        FcmSendDto.Notification.builder()
                                                .title(title)
                                                .body(body)
                                                .build()
                                )
                                .build()
                )
                .build();

    }
}
