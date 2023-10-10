package com.malexj.service.impl;

import com.google.common.collect.Sets;
import com.malexj.model.BillStatus;
import com.malexj.service.BillComparisonService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
public class BillComparisonServiceImpl implements BillComparisonService {

    @Override
    public Mono<Set<BillStatus>> compareBillStatuses(List<BillStatus> requestBillStatus, List<BillStatus> responseBillStatus) {
        Set<BillStatus> diff = Sets.difference(Sets.newHashSet(requestBillStatus), Sets.newHashSet(responseBillStatus));
        // todo: Additional logic
        return Mono.just(diff);
    }
}
