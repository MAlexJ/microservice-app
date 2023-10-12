package com.serjlemast.service;

import com.serjlemast.model.EmailRequest;

public interface EmailSenderService {

    EmailRequest sendEmail(EmailRequest emailRequest);

}
