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
                // 1. Find bill by number in Database
                storageService.findBillByNumber(request.getNumber()) //
                        // 2. Verify bill response
                        // 2.1 Only one dill find by number
                        // 2.2 If not found bill in Database then throw exception -NoSuchBillException
                        .flatMap(verificationService::verifyBillResponse)//
                        // 3. Compare bill statuses
                        // 3.1 if not found difference then send empty result
                        // 3.2 if we have difference in bill statuses then send set of diff statuses
                        .flatMap(statuses -> comparisonService.compareBillStatuses(request.getStatuses(), statuses)) //
                        .filter(diffStatuses -> !diffStatuses.isEmpty()) //
                        // 4. Send diff bill statuses to mail notification service
                        .flatMap(notificationService::sendNotification) //
                        // 5. handle case when bill not found in database then it's new bill and we should save in database
                        .doOnError(error -> errorHandlingService.handleNewBillInRequest(request, error)) //
                        // 6. suppress custom exception in stacktrace logs
                        .onErrorResume(errorHandlingService::suppressNoSuchBillException) //
                        .subscribe());
        return Mono.empty().map(ResponseEntity::ok);
    }
}