package com.malexj.service.impl;

import com.malexj.exception.NoSuchBillException;
import com.malexj.model.request.BillRequest;
import com.malexj.service.ErrorHandlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ErrorHandlingServiceImpl implements ErrorHandlingService {

    @Override
    public void handleError(Throwable error, BillRequest request) {
        if (error instanceof NoSuchBillException) {
            // create bill in database -> request
            log.error(error.getMessage());
        }
    }

}
