package com.malexj.service.impl;

import com.malexj.model.Bill;
import com.malexj.model.BillStatus;
import com.malexj.service.BillComparisonService;
import reactor.core.publisher.Mono;

import java.util.List;

public class BillComparisonServiceImpl implements BillComparisonService {
    @Override
    public Mono<List<BillStatus>> compareBills(List<Bill> reqBills) {
        return null;
    }
}
