package com.malexj.controller;

import com.malexj.model.request.BillRequest;
import com.malexj.service.*;
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
    private CallableService callableService;
    private BillComparisonService comparisonService;
    private NotificationService notificationService;
    private ErrorHandlingService errorHandlingService;
    private BillVerificationService verificationService;

    @PostMapping("/diff")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Object>> diff(@RequestBody BillRequest request) {
        callableService.execute(() -> //
                storageService.findBillByNumber(request.getNumber()) //
                        .flatMap(response -> verificationService.verifyBillResponse(response))//
                        .flatMap(statuses -> comparisonService.compareBillStatuses(request.getStatuses(), statuses)) //
                        .filter(diffStatuses -> !diffStatuses.isEmpty()) //
                        .map(statuses -> notificationService.sendNotification(statuses)) //
                        .doOnError(throwable -> errorHandlingService.handleError(throwable, request)) //
                        .subscribe());
        return Mono.empty().map(ResponseEntity::ok);
    }

}