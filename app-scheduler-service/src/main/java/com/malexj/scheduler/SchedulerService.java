package com.malexj.scheduler;

import com.malexj.service.DiffService;
import com.malexj.service.HtmlService;
import com.malexj.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final HtmlService htmlService;

    private final DiffService diffService;

    private final UserSubscriptionService subscriptionService;

    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void testSubscriptionsApi() {
        subscriptionService.fetchUserSubscriptions().subscribe();
    }

    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void executionScheduledTask() {
        htmlService.fetchBillSearch() //
                .flatMapMany(htmlService::convertSearchResponse) //
                .flatMap(htmlService::fetchBillStatuses) //
                .flatMap(diffService::processingBillStatusDifferences) //
                .doOnError(throwable -> log.error(throwable.getMessage())) //
                .subscribe();
    }

}