package com.malexj.service;

import com.malexj.model.response.DiffResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ErrorHandlingService {

    Mono<ResponseEntity<DiffResponse>> handleError(Throwable error);

    Mono<DiffResponse> buildResponse(String message);
}
