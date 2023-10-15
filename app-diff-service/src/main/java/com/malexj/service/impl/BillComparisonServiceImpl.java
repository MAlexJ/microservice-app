package com.malexj.service.impl;

import com.google.common.collect.Sets;
import com.malexj.model.Bill;
import com.malexj.model.BillStatus;
import com.malexj.model.request.BillDiffRequest;
import com.malexj.model.request.BillRequest;
import com.malexj.service.BillComparisonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillComparisonServiceImpl implements BillComparisonService {

    @Override
    public Mono<BillDiffRequest> compareBillStatuses(BillRequest request, Bill bill) {
        Set<BillStatus> diff = Sets.difference(Sets.newHashSet(request.getStatuses()), Sets.newHashSet(bill.getStatuses()));
        if (diff.isEmpty()) {
            log.info("No difference in bill statuses found");
            return Mono.empty();
        }
        return Mono.just(buildDiffRequest(request, buildDiffList(extractHrefId(bill), diff)));
    }

    private String extractHrefId(Bill bill) {
        return Optional.ofNullable(bill.getSelfLink())
                .map(Bill.Link::getSelf)
                .map(Bill.Self::getHref)
                .orElseThrow(() -> new RuntimeException("bill href link not found"));
    }

    private static List<BillStatus> buildDiffList(String link, Set<BillStatus> diff) {
        return diff.stream() //
                .map(billStatus -> billStatus.addBillLink(link)) //
                .collect(Collectors.toList());
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
