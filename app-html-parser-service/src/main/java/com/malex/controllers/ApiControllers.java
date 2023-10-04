package com.malex.controllers;

import com.malex.mapper.ObjectMapper;
import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.models.request.BillRequest;
import com.malex.models.request.SearchRequest;
import com.malex.models.response.BillResponse;
import com.malex.models.response.SearchResponse;
import com.malex.services.ApiRestService;
import com.malex.services.HtmlPageParsingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class ApiControllers {

    private ObjectMapper mapper;
    private ApiRestService restService;
    private HtmlPageParsingService parsingService;

    @PostMapping("/bills")
    public Mono<BillResponse> findBillStatuses(@RequestBody BillRequest request) {
        log.info("Start processing find bill statuses to search statuses and parse HTML page, request - {}", request);
        return restService.fetchBillStatus(request.getLink()) //
                .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service")) //
                .flatMapMany(html -> parsingService.processBillStatus(html)) //
                .collectList() //
                .map(statuses -> buildBillResponse(request, statuses)) //
                .doOnNext(message -> log.info("Parse API response processing completed, response - {}", message));
    }


    @PostMapping("/searchResults")
    public Mono<SearchResponse> findBillsByCriteria(@RequestBody SearchRequest request) {
        log.info("Start processing find bills and parse HTML page, request - {}", request);
        return restService.fetchSearchResult(request.getLink(), request.getFormUrlencodedData()) //
                .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service")) //
                .flatMapMany(html -> parsingService.processBillSearchResult(html)) //
                .collectList() //
                .map(this::buildSearchResponse) //
                .doOnNext(message -> log.info("Search API response processing completed, response - {}", message));
    }


    private SearchResponse buildSearchResponse(List<Bill> bills) {
        return SearchResponse.builder() //
                .bills(bills) //
                .build();
    }


    private BillResponse buildBillResponse(BillRequest request, List<BillStatus> statuses) {
        BillResponse billResponse = mapper.convertToResponse(request);
        billResponse.setStatuses(statuses);
        return billResponse;
    }

}