package com.serjlemast.service.impl;

import com.serjlemast.exception.EmailException;
import com.serjlemast.model.EmailRequest;
import com.serjlemast.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    @Value("${app-mail-senderFrom}")
    private String mailFrom;

    private final JavaMailSender mailSender;


    @Retryable(maxAttempts = 2, recover = "mailException", backoff = @Backoff(delay = 50000))
    public void sendEmail(EmailRequest emailRequest) {
        handleError(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(emailRequest.getToEmail());
            message.setSubject(emailRequest.getTitle());
            message.setText(emailRequest.getMessage());
            mailSender.send(message);
        });
    }

    @Recover
    public void mailException(EmailException ex, EmailRequest emailRequest) {
        log.error("It is not possible to resend notification - {} by email, error - {}", emailRequest, ex.getMessage());
        throw ex;
    }


    private void handleError(Runnable r) {
        try {
            log.info("Sending notification by email");
            r.run();
            log.info("Email sent successfully");
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new EmailException(ex);
        }
    }
}
