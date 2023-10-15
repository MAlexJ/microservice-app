package com.malexj.service.impl;

import com.malexj.mapper.BilDtoMapper;
import com.malexj.model.request.BillRequest;
import com.malexj.model.request.SearchRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.model.response.SearchResponse;
import com.malexj.service.AbstractService;
import com.malexj.service.HtmlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HtmlServiceImpl extends AbstractService implements HtmlService {


    /**
     * init data
     */
    private static final String SEARCH_BILL_URL = "https://itd.rada.gov.ua/billInfo/Bills/searchResults";
    private final static Map<String, String> FORM_URLENCODED_DATA = Map.of( //
            "BillSearchModel.registrationNumber", "9672", //
            "BillSearchModel.registrationNumberCompareOperation", "2", //
            "BillSearchModel.session", "10", //
            "BillSearchModel.registrationRangeStart", "", //
            "BillSearchModel.registrationRangeEnd", "", //
            "BillSearchModel.name", "", //
            "BillSearchModel.detailView", "false" //
    );

    /**
     * REST API
     */
    @Value("${html-service.base-url}")
    private String baseUrl;

    @Value("${html-service.endpoint.bills}")
    private String billsEndpoint;

    @Value("${html-service.endpoint.searchResults}")
    private String searchResultsEndpoint;

    private final WebClient webClient;

    private final BilDtoMapper mapper;

    @Override
    public Mono<SearchResponse> fetchBillSearch() {
        return webClient.post() //
                .uri(buildUri(baseUrl, searchResultsEndpoint)) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(buildSearchRequest()) //
                .retrieve() //
                .bodyToMono(SearchResponse.class) //
                .doOnNext(response -> log.info("Bills: {}", response.toString()));
    }


    @Override
    public Mono<BillResponse> fetchBillStatuses(BillRequest request) {
        return webClient.post() //
                .uri(buildUri(baseUrl, billsEndpoint)) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(request) //
                .retrieve() //
                .bodyToMono(BillResponse.class) //
                .doOnNext(response -> log.info("Statuses: " + response.getStatuses()));
    }

    @Override
    public Flux<BillRequest> convertSearchResponse(SearchResponse response) {
        List<BillRequest> bills = response.getBills().stream() //
                .map(mapper::responseMapper) //
                .collect(Collectors.toList());
        return Flux.fromIterable(bills);
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
