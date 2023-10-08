package com.malexj.controller;

import com.google.common.collect.Sets;
import com.malexj.exception.BillStatusDifferenceException;
import com.malexj.exception.NoSuchBillException;
import com.malexj.model.Bill;
import com.malexj.model.BillStatus;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.DiffResponse;
import com.malexj.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

    private StorageService storageService;

    @PostMapping("/diff")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<DiffResponse>> diff(@RequestBody BillRequest request) {
        return storageService.findBillByNumber(request.getNumber()) //
                .flatMap(response -> {
                    return Mono.just(response.getEmbedded().getBills());
                }) //
                .flatMap(bills -> verifyBillResponse(bills)) //
                .flatMap(statuses -> compareBillStatuses(request.getStatuses(), statuses)) //
                .map(ResponseEntity::ok) //
                .onErrorResume(this::handleError);
    }

    private Mono<List<BillStatus>> verifyBillResponse(List<Bill> bills) {
        Bill bill = bills.stream() //
                .findFirst() //
                .orElseThrow((() -> new NoSuchBillException("Bill not found in the database by number")));
        return Mono.just(bill.getStatuses());
    }


    private Mono<DiffResponse> compareBillStatuses(List<BillStatus> reqStatuses, List<BillStatus> dbStatuses) {
        Set<BillStatus> diff = Sets.difference(Sets.newHashSet(reqStatuses), Sets.newHashSet(dbStatuses));
        if (!diff.isEmpty()) {
            return Mono.error(new BillStatusDifferenceException(diff));
        }
        if (reqStatuses.isEmpty() && !dbStatuses.isEmpty()) {
            return Mono.error(new RuntimeException("Bil request doesn't contain statuses that are in database"));
        }
        return Mono.just(buildResponse("Bill statuses are equal"));
    }


    private Mono<ResponseEntity<DiffResponse>> handleError(Throwable error) {
        ResponseEntity<DiffResponse> response;
        if (error instanceof NoSuchBillException) {
            response = builResponseEntity(204, error.getMessage());
        } else if (error instanceof BillStatusDifferenceException) {
            response = builResponseEntity(404, error.getMessage());
        } else {
            response = builResponseEntity(500, error.getMessage());
        }
        return Mono.just(response);
    }


    private ResponseEntity<DiffResponse> builResponseEntity(int status, String message) {
        return ResponseEntity //
                .status(status) //
                .body(buildResponse(message));
    }


    private DiffResponse buildResponse(String message) {
        return DiffResponse.builder() //
                .message(message) //
                .build();
    }
}
