package com.malexj.service;

import com.malexj.model.request.BillRequest;
import reactor.core.publisher.Mono;

public interface ErrorHandlingService {

    /**
     * Handle the case when {@link com.malexj.model.Bill} is not found in database,
     * then the new {@link com.malexj.model.Bill} we have to save it in database
     */
    void handleNewBillInRequest(BillRequest request, Throwable error);

    /**
     * Suppress custom exception in stacktrace logs
     */
    <T> Mono<T> suppressNoSuchBillException(Throwable error);
}
