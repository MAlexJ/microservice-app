package com.serjlemast.service.impl;

import com.serjlemast.model.EmailRequest;
import com.serjlemast.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    @Value("${app-mail-senderFrom}")
    private String mailFrom;

    private final JavaMailSender mailSender;


    public void sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(emailRequest.getToEmail());
        message.setSubject(emailRequest.getTitle());
        message.setText(emailRequest.getMessage());
        mailSender.send(message);
        log.info("Email sent successfully");
    }
}
