package com.serjlemast.service;

import com.serjlemast.model.request.EmailRequest;

public interface EmailSenderService {

    EmailRequest sendEmail(EmailRequest emailRequest);

}
