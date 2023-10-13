package com.malexj.service;

import com.malexj.model.Bill;
import com.malexj.model.request.BillDiffRequest;
import com.malexj.model.request.BillRequest;
import reactor.core.publisher.Mono;

public interface BillComparisonService {

    /**
     * Compare bill statuses
     * if we have a difference in bill statuses, then bill status has changed
     * and subscribers should be notified using {@link NotificationService#sendNotification(BillDiffRequest)}
     * otherwise there is no point in continuing to process requests - empty result
     */
    Mono<BillDiffRequest> compareBillStatuses(BillRequest request, Bill bill);

}