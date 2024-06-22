package com.example.alarmservice.alarm.infrastrucutre.port;

import com.example.alarmservice.alarm.controller.port.FcmSender;
import com.example.alarmservice.alarm.dto.firebase.FcmDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class FcmSenderToOne implements FcmSender {

    @Value("${firebase.API_URL}")
    private String apiUrl;

    @Override
    public void sendMessage(FcmDto fcmDto) {
        try {
            String message = getMessage(fcmDto);
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(requestBody)
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();

            okHttpClient.newCall(request).execute();

        } catch (IOException ignored) {
            log.error(ignored.getMessage());
        }
    }

    private String getAccessToken() throws IOException {
        String firebasePath = "firebase/testing-3fbd5-firebase-adminsdk-jzr1w-d99745a2ac.json";
        GoogleCredentials scoped = GoogleCredentials.
                fromStream(new ClassPathResource(firebasePath).getInputStream())
                .createScoped(List.of("<https://www.googleapis.com/auth/cloud-platform>"));
        scoped.refreshIfExpired();
        return scoped.getAccessToken().getTokenValue();
    }

    private String getMessage(FcmDto fcmDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(fcmDto);
    }
}
