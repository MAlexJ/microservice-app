package com.serjlemast.service;

import com.serjlemast.model.EmailRequest;

public interface EmailSenderService {

    void sendEmail(EmailRequest emailRequest);

}
