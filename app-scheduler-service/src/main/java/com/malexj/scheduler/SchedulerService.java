package com.malexj.scheduler;

import com.malexj.service.DiffService;
import com.malexj.service.HtmlService;
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