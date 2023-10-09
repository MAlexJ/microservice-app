package com.malexj.service.impl;

import com.malexj.exception.NoSuchBillException;
import com.malexj.exception.NoSuchBillStatusException;
import com.malexj.model.Bill;
import com.malexj.model.BillStatus;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.service.BillVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BillVerificationServiceImpl implements BillVerificationService {

    public Mono<List<BillStatus>> verifyBillResponse(BillRequest request, BillResponse response) {
        String reqNumber = request.getNumber();
        List<BillStatus> requestStatuses = request.getStatuses();

        List<Bill> respBills = Optional.ofNullable(response.getEmbedded()) //
                .map(BillResponse.Embedded::getBills) //
                .filter(bills -> !CollectionUtils.isEmpty(bills)) //
                .orElseThrow((() -> new NoSuchBillException("Bill not found in the database by number - " + reqNumber)));

        if (respBills.size() > 1) {
            log.warn("more than one bill found by number - {}, bills - {}", requestStatuses, respBills);
        }

        List<BillStatus> respStatuses = Optional.of(respBills) //
                .flatMap(bills -> bills.stream().findFirst()) //
                .map(Bill::getStatuses) //
                .filter(statuses -> allBillStatusesAreNotEmpty(statuses, requestStatuses)) //
                .orElseThrow((() -> new NoSuchBillStatusException("Request and response bill statuses are different")));

        return Mono.just(respStatuses);
    }

    private boolean allBillStatusesAreNotEmpty(List<BillStatus> requestStatuses, List<BillStatus> responseStatuses) {
        return !requestStatuses.isEmpty() && !responseStatuses.isEmpty();
    }
}
