package com.malexj.service;

import com.malexj.model.request.BillDiffRequest;
import com.malexj.model.request.BillRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ErrorHandlingService {

    /**
     * Handle the case when {@link com.malexj.model.Bill} is not found in database,
     * then the new {@link com.malexj.model.Bill} we have to save it in database
     */
    void handleNewBill(BillRequest request, Throwable error);


    /**
     * Handle the case when {@link com.malexj.model.BillStatus} is not found in database,
     * then new {@link com.malexj.model.BillStatus} we have to save
     */
    Flux<String> handleNewBillStatuses(BillDiffRequest diffRequest);

    /**
     * Suppress custom exception in stacktrace logs
     */
    <T> Mono<T> suppressNoSuchBillException(Throwable error);
}
