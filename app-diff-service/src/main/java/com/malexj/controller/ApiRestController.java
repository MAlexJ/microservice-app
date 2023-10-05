package com.malexj.controller;

import com.malexj.mapper.ObjectMapper;
import com.malexj.model.Bill;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

    private StorageService storageService;
    private ObjectMapper mapper;

    @PostMapping("/diff")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<BillResponse>> diff(@RequestBody BillRequest request) {

        // 1. find bill by number
        Mono<ResponseEntity<BillResponse>> bodyBuilderMono = //
                storageService.findBillByNumber(request.getNumber()) //
       // 2. convert BillResponse to Bill
                        .doOnNext(response -> {
                            List<Bill> bills = response.getEmbedded().getBills();
                            if (!bills.isEmpty()) {
                                Bill expectedBill = bills.stream().findFirst().get();
                                Bill actualBill = mapper.convertToResponse(request);
                                log.info("equals = " + expectedBill.equals(actualBill));
                            }
                        }) //
                        .map(ResponseEntity::ok).doOnError(r -> ResponseEntity.internalServerError());


        // 3. compare bill

        // 4.1 object are equals - do nothing

        // 4.2 object are difference - send notification

        return bodyBuilderMono;
    }

}
