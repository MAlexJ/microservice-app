package com.malexj.scheduler;

import com.malexj.mapper.BilDtoMapper;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.SearchResponse;
import com.malexj.service.DiffService;
import com.malexj.service.HtmlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final HtmlService htmlService;

    private final DiffService diffService;

    private final BilDtoMapper mapper;


    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void executionScheduledTask() {
        htmlService.fetchSearchBill() //
                .flatMapMany(this::createBillRequest) //
                .flatMap(htmlService::fetchBillStatuses) //
                .flatMap(diffService::handleDifferences) //
                .doOnError(throwable -> log.error(throwable.getMessage())) //
                .subscribe();
    }

    private Flux<BillRequest> createBillRequest(SearchResponse response) {
        List<BillRequest> bills = response.getBills().stream() //
                .map(mapper::responseMapper) //
                .collect(Collectors.toList());
        return Flux.fromIterable(bills);
    }

}