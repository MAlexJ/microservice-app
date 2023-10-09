package com.malexj.service;

import com.malexj.model.BillStatus;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BillVerificationService {

    Mono<List<BillStatus>> verifyBillResponse(BillRequest request, BillResponse response);

}
