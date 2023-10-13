package com.malexj.service.impl;

import com.google.common.collect.Sets;
import com.malexj.model.Bill;
import com.malexj.model.BillStatus;
import com.malexj.model.request.BillDiffRequest;
import com.malexj.model.request.BillRequest;
import com.malexj.service.BillComparisonService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BillComparisonServiceImpl implements BillComparisonService {

    @Override
    public Mono<BillDiffRequest> compareBillStatuses(BillRequest request, Bill bill) {
        Set<BillStatus> diff = Sets.difference(Sets.newHashSet(request.getStatuses()), Sets.newHashSet(bill.getStatuses()));
        if (diff.isEmpty()) {
            return Mono.empty();
        }
        return Mono.just(buildDiffRequest(request, new ArrayList<>(diff)));
    }

    private BillDiffRequest buildDiffRequest(BillRequest request, List<BillStatus> diff) {
        return BillDiffRequest.builder() //
                .user(request.getUser()) //
                .number(request.getNumber()) //
                .name(request.getName()) //
                .diffStatuses(diff) //
                .build();
    }
}
