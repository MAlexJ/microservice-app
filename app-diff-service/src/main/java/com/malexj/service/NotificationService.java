package com.malexj.service;

import com.malexj.model.request.BillDiffRequest;
import reactor.core.publisher.Mono;

public interface NotificationService {

    /**
     * Send notification to mail-service about changing the status of bill
     */
    Mono<Object> sendNotification(BillDiffRequest request);

}
