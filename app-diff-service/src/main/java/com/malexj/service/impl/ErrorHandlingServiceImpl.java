package com.malexj.service.impl;

import com.malexj.exception.NoSuchBillException;
import com.malexj.model.request.BillRequest;
import com.malexj.service.AsyncService;
import com.malexj.service.ErrorHandlingService;
import com.malexj.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorHandlingServiceImpl implements ErrorHandlingService {

    private final StorageService storageService;
    private final AsyncService asyncService;

    @Override
    public void handleNewBillInRequest(BillRequest request, Throwable error) {
        if (error instanceof NoSuchBillException) {
            asyncService.execute(() -> saveNewBillInDatabase(request));
        }
    }

    private Disposable saveNewBillInDatabase(BillRequest request) {
        return storageService.save(request) //
                .doOnNext(response -> log.info("Bill - {} has been successfully saved in database.", response.getEmbedded())) //
                .subscribe();
    }

    @Override
    public <T> Mono<T> suppressNoSuchBillException(Throwable error) {
        if (error instanceof NoSuchBillException) {
            log.warn(error.getMessage());
            return Mono.empty();
        }
        return Mono.error(error);
    }

}
