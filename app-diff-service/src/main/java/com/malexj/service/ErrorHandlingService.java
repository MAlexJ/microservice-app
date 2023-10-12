package com.malexj.service;

import com.malexj.model.request.BillRequest;
import reactor.core.publisher.Mono;

public interface ErrorHandlingService {

    void handleNewBillInRequest(BillRequest request, Throwable error);


    <T> Mono<T> suppressNoSuchBillException(Throwable error);
}
