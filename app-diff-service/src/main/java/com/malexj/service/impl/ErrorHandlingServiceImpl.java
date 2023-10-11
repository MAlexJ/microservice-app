package com.malexj.service.impl;

import com.malexj.exception.NoSuchBillException;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.service.ErrorHandlingService;
import com.malexj.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class ErrorHandlingServiceImpl implements ErrorHandlingService {

    private StorageService storageService;

    @Override
    public void handleError(Throwable error, BillRequest request) {
        if (error instanceof NoSuchBillException) {

            // it doesn't work !!
            Mono<BillResponse> billResponseMono = storageService.saveNewBill(request);
             billResponseMono //
                     .doOnNext(response -> log.info(">>> " + response));
        }
        throw new RuntimeException(error.getMessage());
    }

}
