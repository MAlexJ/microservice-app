package com.malexj.controller;

import com.malexj.model.request.BillRequest;
import com.malexj.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

    private final StorageService storageService;
    private final CallableService callableService;
    private final BillComparisonService comparisonService;
    private final NotificationService notificationService;
    private final ErrorHandlingService errorHandlingService;
    private final BillVerificationService verificationService;

    @PostMapping("/diff")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Object>> diff(@RequestBody BillRequest request) {
        callableService.execute(() -> //
                storageService.findBillByNumber(request.getNumber()) //
                        .flatMap(verificationService::verifyBillResponse) //
                        .flatMap(bill -> comparisonService.compareBillStatuses(request, bill)) //
                        .flatMap(notificationService::sendNotification) //
                        .doOnError(error -> errorHandlingService.handleNewBillInRequest(request, error)) //
                        .onErrorResume(errorHandlingService::suppressNoSuchBillException) //
                        .subscribe());
        return Mono.empty().map(ResponseEntity::ok);
    }
}