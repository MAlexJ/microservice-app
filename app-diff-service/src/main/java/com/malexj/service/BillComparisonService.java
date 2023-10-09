package com.malexj.service;

import com.malexj.model.BillStatus;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface BillComparisonService {

    Mono<Set<BillStatus>> compareBillStatuses(List<BillStatus> requestBillStatus, List<BillStatus> responseBillStatus);

}