package com.malexj.service;

import com.malexj.model.response.BillResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DiffService {

    Mono<ResponseEntity<Void>> processingBillStatusDifferences(BillResponse billResponse);
}
