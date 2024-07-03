package com.example.alarmservice.alarm.infrastrucutre;

public interface TokenRepository {
    public String saveToken(String id, String token);

    public String findTokenById(String id);

    public String deleteToken(String id);

    public String updateToken(String id, String token);
}
