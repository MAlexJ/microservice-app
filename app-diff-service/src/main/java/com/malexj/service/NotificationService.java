package com.malexj.service;

import com.malexj.model.BillStatus;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface NotificationService {

    Mono<Set<BillStatus>> sendNotification(Set<BillStatus> statuses);

}
