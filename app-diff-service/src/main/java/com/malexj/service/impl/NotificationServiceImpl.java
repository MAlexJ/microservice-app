package com.malexj.service.impl;

import com.malexj.model.BillStatus;
import com.malexj.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public Mono<Set<BillStatus>> sendNotification(Set<BillStatus> statuses) {
        log.info(">>> Send diff to notification service");
        return Mono.just(statuses);
    }
}
