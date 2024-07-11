package com.example.userservice.user.infrastructure.mail;

public interface MailSender {

    void send(String email, String title, String content);
}
