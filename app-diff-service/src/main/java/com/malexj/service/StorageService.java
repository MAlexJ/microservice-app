package com.malexj.service;

import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import reactor.core.publisher.Mono;

public interface StorageService {

    /**
     * Find bill by number in app-storage-service
     * @param number - bill number
     * @return Bill representation
     */
    Mono<BillResponse> findBillByNumber(String number);

    Mono<BillResponse> save(BillRequest request);
}
