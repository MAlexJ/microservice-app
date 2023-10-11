package com.serjlemast.controller;

import com.serjlemast.mapper.ObjectMapper;
import com.serjlemast.model.request.EmailRequest;
import com.serjlemast.model.response.EmailResponse;
import com.serjlemast.service.impl.EmailSenderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mail")
public class ApiRestController {

    private ObjectMapper mapper;
    private EmailSenderServiceImpl emailSender;

    @PostMapping("/send")
    public EmailResponse findBillStatuses(@RequestBody EmailRequest request) {
        log.info("Start processing sending email, request - ", request);
        EmailRequest emailRequest = emailSender.sendEmail(request);
        return buildEmailResponse(emailRequest, "Successfully");
    }

    private EmailResponse buildEmailResponse(EmailRequest request, String status) {
        EmailResponse emailResponse = mapper.convertToResponse(request);
        emailResponse.setStatus(status);
        return emailResponse;
    }

}
