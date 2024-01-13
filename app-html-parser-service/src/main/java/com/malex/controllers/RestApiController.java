package com.malex.controllers;

import com.malex.mapper.ObjectMapper;
import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.models.request.BillRequest;
import com.malex.models.request.SearchRequest;
import com.malex.models.response.BillResponse;
import com.malex.models.response.SearchResponse;
import com.malex.services.HtmlPageParsingService;
import com.malex.services.ProxyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RestApiController {

  // remove it !!!!
  private final ObjectMapper mapper;

  private final ProxyService proxyService;
  private final HtmlPageParsingService parsingService;

  /**
   * Find bills by criteria:
   *
   * <p>Request body {@link SearchRequest}
   */
  @PostMapping("/searchResults")
  public Mono<SearchResponse> findBillsByCriteria(@RequestBody SearchRequest request) {
    log.info("Start processing search bills, request - {}", request);
    return proxyService
        .fetchSearchResult(request)
        .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service"))
        .flatMapMany(parsingService::processBillSearchResult)
        .collectList()
        .map(this::buildSearchResponse)
        .doOnNext(message -> log.info("Search processing completed, response - {}", message));
  }

  /**
   * Find bill statuses by link, name, number and registrationDate
   *
   * <p>Request body {@link BillRequest}
   */
  @PostMapping("/bills")
  public Mono<BillResponse> findBillStatuses(@RequestBody BillRequest request) {
    log.info("Start processing find bill statuses to search statuses, request - {}", request);
    return proxyService
        .fetchProxyRequest(request)
        .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service"))
        .flatMapMany(parsingService::processBillStatus)
        .collectList()
        .map(statuses -> buildBillResponse(request, statuses))
        .doOnNext(message -> log.info("Processing completed, response - {}", message));
  }

  private SearchResponse buildSearchResponse(List<Bill> bills) {
    return SearchResponse.builder().bills(bills).build();
  }

  private BillResponse buildBillResponse(BillRequest request, List<BillStatus> statuses) {
    BillResponse billResponse = mapper.convertToResponse(request);
    billResponse.setStatuses(statuses);
    return billResponse;
  }
}
