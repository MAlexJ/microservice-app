package com.malex.controller;

import com.malex.jsoup.HtmlPageParsingService;
import com.malex.model.api.request.BillStatusesRequest;
import com.malex.model.api.request.BillsRequest;
import com.malex.model.api.response.BillStatusesResponse;
import com.malex.model.api.response.BillsResponse;
import com.malex.service.ProxyService;
import com.malex.util.RestControllerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RestApiController {

  private final RestControllerUtils utils;
  private final HtmlPageParsingService parsingService;
  private final ProxyService proxyService;

  /**
   * Find bills by criteria:
   *
   * <p>Request body {@link BillsRequest}
   */
  @PostMapping("/searchResults")
  public Mono<BillsResponse> findBillsByFormUrlencodedData(@RequestBody BillsRequest request) {
    log.info("Start processing search bills, request - {}", request);
    return proxyService
        .redirectApiRequestToProxyWebservice(request)
        .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service"))
        .flatMapMany(parsingService::processBillSearchResult)
        .collectList()
        .map(utils::buildSearchResponse)
        .doOnNext(message -> log.info("Search processing completed, response - {}", message));
  }

  /**
   * Find bill statuses by link, name, number and registrationDate
   *
   * <p>Request body {@link BillStatusesRequest}
   */
  @PostMapping("/bills")
  public Mono<BillStatusesResponse> findBillStatuses(@RequestBody BillStatusesRequest request) {
    log.info("Start processing find bill statuses to search statuses, request - {}", request);
    return proxyService
        .redirectApiRequestToProxyWebservice(request)
        .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service"))
        .flatMapMany(parsingService::processBillStatus)
        .collectList()
        .map(statuses -> utils.buildBillResponse(request, statuses))
        .doOnNext(message -> log.info("Processing completed, response - {}", message));
  }
}
