package com.malexj.service;

import com.malexj.model.response.BillResponse;
import reactor.core.publisher.Mono;

public interface StorageService {

    Mono<BillResponse> findBillByNumber(String number);
}
