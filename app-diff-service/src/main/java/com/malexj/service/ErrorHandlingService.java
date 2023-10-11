package com.malexj.service;

import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import reactor.core.publisher.Mono;

public interface ErrorHandlingService {

    void handleError(Throwable error, BillRequest request);
}
