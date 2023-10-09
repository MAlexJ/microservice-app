package com.malexj.controller;

import com.malexj.model.request.BillRequest;
import com.malexj.model.response.DiffResponse;
import com.malexj.service.BillComparisonService;
import com.malexj.service.BillVerificationService;
import com.malexj.service.ErrorHandlingService;
import com.malexj.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

    private StorageService storageService;
    private BillComparisonService comparisonService;
    private ErrorHandlingService errorHandlingService;
    private BillVerificationService verificationService;

    @PostMapping("/diff")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<DiffResponse>> diff(@RequestBody BillRequest request) {
        return storageService.findBillByNumber(request.getNumber()) //
                .flatMap(response -> verificationService.verifyBillResponse(request, response))//
                .flatMap(statuses -> comparisonService.compareBillStatuses(request.getStatuses(), statuses)) //
                .flatMap(billStatuses -> buildResponse("Bill statuses are equal")) //
                .map(ResponseEntity::ok) //
                .onErrorResume(error -> errorHandlingService.handleError(error));
    }


    public Mono<DiffResponse> buildResponse(String message) {
        return Mono.just(DiffResponse.builder().message(message).build());
    }
}
