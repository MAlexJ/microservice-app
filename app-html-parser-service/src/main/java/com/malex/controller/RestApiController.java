package com.malex.controller;

import com.malex.jsoup.HtmlPageParsingService;
import com.malex.mapper.ObjectMapper;
import com.malex.model.api.request.BillStatusesRequest;
import com.malex.model.api.request.BillsRequest;
import com.malex.model.api.response.BillStatusesResponse;
import com.malex.model.api.response.BillsResponse;
import com.malex.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1")
public class RestApiController extends AbstractRestApiController {

  public RestApiController(
      HtmlPageParsingService htmlService, ProxyService proxyService, ObjectMapper mapper) {
    super(htmlService, proxyService, mapper);
  }

  /**
   * Find bills by criteria:
   *
   * <p>Request body {@link BillsRequest}
   */
  @PostMapping("/bills")
  public Mono<ResponseEntity<BillsResponse>> findBills(@RequestBody BillsRequest request) {
    log.info("HTTP: find bills, request - {}", request);
    return proxyService
        .redirectRequestToProxyWebservice(request)
        .flatMapMany(parsingService::htmlProcessingBills)
        .collectList()
        .map(this::buildSuccessfulResponse)
        .doOnNext(message -> log.info("HTTP: find bills, response - {}", message))
        .onErrorResume(throwable -> handleFallbackResponse(throwable, buildFallbackResponse()));
  }

  /**
   * Find bill statuses by link, name, number and registrationDate
   *
   * <p>Request body {@link BillStatusesRequest}
   */
  @PostMapping("/bill/statuses")
  public Mono<ResponseEntity<BillStatusesResponse>> findBillStatuses(
      @RequestBody BillStatusesRequest request) {
    log.info("HTTP: find bill statuses, request - {}", request);
    return proxyService
        .redirectRequestToProxyWebservice(request)
        .flatMapMany(parsingService::htmlProcessingBillStatus)
        .collectList()
        .map(statuses -> buildSuccessfulResponseEntity(request, statuses))
        .doOnNext(message -> log.info("HTTP: find bill statuses, response - {}", message))
        .onErrorResume(
            throwable -> handleFallbackResponse(throwable, buildFallbackResponse(request)));
  }
}
