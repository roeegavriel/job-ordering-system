package com.roee.joborderingsystem.mail;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailService {

    private final Boolean active;

    private final String fromMail;

    private final JavaMailSender emailSender;

    public EmailService(@Value("${spring.mail.x-active}") Boolean active, @Value("${spring.mail.username}") String fromMail, JavaMailSender emailSender) {
        this.active = active;
        this.fromMail = fromMail;
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        if (!active) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @PostConstruct
    void init() {
        if (!active) {
            log.warn("Email service is not active, mails won't be sent");
        }
    }
}
