package com.malexj.service;

import com.malexj.exception.NoSuchBillException;
import com.malexj.model.Bill;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import reactor.core.publisher.Mono;

public interface BillVerificationService {

    /**
     * Verify bill response.
     * If no Bill is found in storage-service response then NoSuchBillException exception is thrown
     * {@link NoSuchBillException} exception will be caught in error handler
     * {@link ErrorHandlingService#handleNewBill(BillRequest, Throwable)} and this will mean
     * that Bill is new and will be saved in the database by storage-service.
     *
     * @param response - Bill representation
     * @return Bill with statuses
     */
    Mono<Bill> verifyBillResponse(BillResponse response);

}
