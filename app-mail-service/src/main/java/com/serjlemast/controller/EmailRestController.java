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
@RequestMapping("/v1")
public class EmailRestController extends AbstractController {

    private final EmailSenderServiceImpl emailSender;


    /**
     * Design REST api URL - <a href="https://stackoverflow.com/questions/12310442/how-to-design-rest-api-for-email-sending-service">stackoverflow</a>
     * and book - <a href="https://www.oreilly.com/library/view/rest-api-design/9781449317904/">REST API Design Rulebook</a>
     */
    @PostMapping("/emails")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest request) {
        log.info("Start processing sending email, request - {}", request);
        emailSender.sendEmail(request);
        log.info("End processing sending");
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler({MailException.class})
    public ResponseEntity<String> handleMailException(Exception ex) {
        log.error(ex.getMessage());
        return buildResponseEntityWithException(ex);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleAllException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}