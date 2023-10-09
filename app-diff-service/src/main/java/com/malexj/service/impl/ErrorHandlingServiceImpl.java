package com.malexj.service.impl;

import com.malexj.exception.BillStatusDifferenceException;
import com.malexj.exception.NoSuchBillException;
import com.malexj.exception.NoSuchBillStatusException;
import com.malexj.model.response.DiffResponse;
import com.malexj.service.ErrorHandlingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ErrorHandlingServiceImpl implements ErrorHandlingService {

    @Override
    public Mono<ResponseEntity<DiffResponse>> handleError(Throwable error) {
        if (error instanceof NoSuchBillException) {
            return buildResponseEntity(204, error.getMessage());
        } else if (error instanceof BillStatusDifferenceException) {
            return buildResponseEntity(404, error.getMessage());
        } else if (error instanceof NoSuchBillStatusException) {
            return buildResponseEntity(404, error.getMessage());
        }
        return buildResponseEntity(500, error.getMessage());
    }


    private Mono<ResponseEntity<DiffResponse>> buildResponseEntity(int status, String message) {
        DiffResponse response = DiffResponse.builder() //
                .message(message) //
                .build();
        return Mono.just(response) //
                .map(diffResponse -> ResponseEntity.status(status).body(diffResponse));
    }

}
