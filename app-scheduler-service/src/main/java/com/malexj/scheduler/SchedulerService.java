package com.malexj.scheduler;

import com.malexj.mapper.BilDtoMapper;
import com.malexj.model.request.BillRequest;
import com.malexj.model.request.SearchRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.model.response.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class SchedulerService {

    /**
     * API URL
     */
    private final static String SEARCH_API_URL = "http://localhost:8085/v1/searchResults";
    private final static String BILL_STATUSES_API_URL = "http://localhost:8085/v1/bills";

    /**
     * init data
     */
    private static final String SEARCH_BILL_URL = "https://itd.rada.gov.ua/billInfo/Bills/searchResults";
    private final static Map<String, String> FORM_URLENCODED_DATA = Map.of( //
            "BillSearchModel.registrationNumber", "9672", //
            "BillSearchModel.registrationNumberCompareOperation", "2");

    private WebClient webClient;
    private BilDtoMapper mapper;


    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void executionScheduledTask() {
        handleErrors(() -> {
            Mono<SearchResponse> billResponseMono = fetchSearchBill();
            Flux<BillResponse> billStatuses = fetchBillStatuses(billResponseMono);
            billStatuses.subscribe();
        });
    }


    private void handleErrors(Runnable r) {
        try {
            r.run();
        } catch (WebClientRequestException ex) {
            log.warn(ex.getMessage());
        }
    }


    private Mono<SearchResponse> fetchSearchBill() {
        return webClient.post() //
                .uri(SEARCH_API_URL) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(buildSearchRequest()) //
                .retrieve() //
                .bodyToMono(SearchResponse.class) //
                .doOnNext(response -> log.info("Bills found - {}", response.toString()));
    }


    private Flux<BillResponse> fetchBillStatuses(Mono<SearchResponse> response) {
        return response.flatMapMany(r -> Flux.fromIterable(buildBillRequest(r))) //
                .flatMap(request -> webClient.post() //
                        .uri(BILL_STATUSES_API_URL) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .bodyValue(request) //
                        .retrieve() //
                        .bodyToMono(BillResponse.class)) //
                .doOnNext(message -> log.info("  >>> BillResponse: " + message.toString()));
    }


    private List<BillRequest> buildBillRequest(SearchResponse response) {
        return response.getBills().stream() //
                .map(bill -> mapper.responseMapper(bill)) //
                .collect(Collectors.toList()); //
    }


    private SearchRequest buildSearchRequest() {
        return SearchRequest.builder() //
                .link(SEARCH_BILL_URL) //
                .formUrlencodedData(buildFormData()) //
                .build();
    }


    private List<SearchRequest.FormUrlencodedData> buildFormData() {
        return FORM_URLENCODED_DATA.entrySet().stream() //
                .map(entry -> new SearchRequest.FormUrlencodedData(entry.getKey(), entry.getValue())) //
                .toList();
    }

}
