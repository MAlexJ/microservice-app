package com.serjlemast.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;

public abstract class AbstractController {

    public ResponseEntity<String> buildResponseEntityWithException(Exception ex) {
        StringBuilder sb = new StringBuilder();
        if (ex instanceof MailParseException) {
            sb.append(String.format("Failure when parsing message, ex: %s", ex.getMessage()));
        }
        if (ex instanceof MailAuthenticationException) {
            sb.append(String.format("Authentication failure, ex: %s", ex.getMessage()));
        }
        if (ex instanceof MailException) {
            sb.append(String.format("Cant send message, ex: %s", ex.getMessage()));
        }
        if (sb.isEmpty()) {
            sb.append(ex.getMessage());
        }
        String errorMessage = sb.toString();
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
