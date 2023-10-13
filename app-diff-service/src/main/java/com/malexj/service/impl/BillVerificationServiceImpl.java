package com.malexj.service.impl;

import com.malexj.exception.NoSuchBillException;
import com.malexj.model.Bill;
import com.malexj.model.response.BillResponse;
import com.malexj.service.BillVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class BillVerificationServiceImpl implements BillVerificationService {

    public Mono<Bill> verifyBillResponse(BillResponse response) {
        BillResponse.Page page = response.getPage();
        if (Objects.nonNull(page) && page.getTotalElements() > 1) {
            log.warn("more than one bill found in database, bills - {}", response.getEmbedded());
        }
        return Mono.just(extractBillStatuses(response));
    }

    private Bill extractBillStatuses(BillResponse response) {
        return Optional.ofNullable(response.getEmbedded()) //
                .map(BillResponse.Embedded::getBills) //
                .filter(bills -> !CollectionUtils.isEmpty(bills)) //
                .flatMap(bills -> bills.stream().findFirst()) //
                .orElseThrow((() -> new NoSuchBillException("Bill not found in database")));
    }

}
