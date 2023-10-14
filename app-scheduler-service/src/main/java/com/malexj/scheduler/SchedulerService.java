package com.malexj.scheduler;

import com.malexj.model.response.BillResponse;
import com.malexj.model.response.SearchResponse;
import com.malexj.service.DiffService;
import com.malexj.service.HtmlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final HtmlService htmlService;

    private final DiffService diffService;


    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void executionScheduledTask() {
        handleErrors(() -> {
            Mono<SearchResponse> billResponseMono = htmlService.fetchSearchBill();
            Flux<BillResponse> billStatuses = htmlService.fetchBillStatuses(billResponseMono);
            Flux<String> diffResponseFlux = diffService.fetchDiff(billStatuses);
            diffResponseFlux.subscribe();
        });
    }


    private void handleErrors(Runnable r) {
        try {
            r.run();
        } catch (WebClientRequestException ex) {
            log.warn(ex.getMessage());
        }
    }

}
