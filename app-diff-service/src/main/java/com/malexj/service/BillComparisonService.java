package com.malexj.service;

import com.malexj.model.Bill;
import com.malexj.model.BillStatus;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BillComparisonService {

    Mono<List<BillStatus>> compareBills(List<Bill> reqBills);


}
