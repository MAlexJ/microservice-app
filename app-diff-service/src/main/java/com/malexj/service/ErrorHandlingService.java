package com.malexj.service;

import com.malexj.model.request.BillRequest;

public interface ErrorHandlingService {

    void handleError(Throwable error, BillRequest request);
}
