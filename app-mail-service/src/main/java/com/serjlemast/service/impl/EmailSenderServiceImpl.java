package com.serjlemast.service.impl;

import com.serjlemast.model.request.EmailRequest;
import com.serjlemast.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailRequest sendEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("maksiomrpp@gmail.com");
        message.setTo(emailRequest.getToEmail());
        message.setText(emailRequest.getTitle());
        message.setSubject(emailRequest.getMessage());
        log.info("Sending email...");
        mailSender.send(message);
        log.info("Email sent successfully.");
        return emailRequest;
    }
}
