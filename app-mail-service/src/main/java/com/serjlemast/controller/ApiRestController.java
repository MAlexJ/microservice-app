package com.serjlemast.controller;

import com.serjlemast.model.EmailRequest;
import com.serjlemast.service.impl.EmailSenderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class ApiRestController {

    private final EmailSenderServiceImpl emailSender;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest request) {
        log.info("Start processing sending email, request - {}", request);
        emailSender.sendEmail(request);
        log.info("End processing sending");
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({MailException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
        return new ResponseEntity<>("Cant send message, ex: " + ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
