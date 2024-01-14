package com.malexj.controller;

import com.malexj.model.api.BillStatusRequest;
import com.malexj.model.api.BillsRequest;
import com.malexj.model.api.RestApiResponse;
import com.malexj.service.ProxyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class RestApiController {

  private static final String STATUSES_REQUEST_LOG_TAGS = "HTTP API: Bill statuses request - {}";
  private static final String STATUSES_RESPONSE_LOG_TAGS = "HTTP API: Bill statuses response: {}";

  private static final String BILLS_REQUEST_LOG_TAGS = "HTTP API: Bills request - {}";
  private static final String BILLS_RESPONSE_LOG_TAGS = "HTTP API: Bills response: {}";

  private final ProxyService proxyService;

  @Operation(summary = "Search bills status using PROXY webservice")
  @PostMapping(
      value = "/bill/statuses",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<RestApiResponse>> findBillStatuses(
      @RequestBody BillStatusRequest request) {
    log.info(STATUSES_REQUEST_LOG_TAGS, request);
    return proxyService
        .redirectRequestToProxy(request)
        .doOnNext(response -> log.info(STATUSES_RESPONSE_LOG_TAGS, response))
        .map(ResponseEntity::ok)
        .switchIfEmpty(buildUserNotFoundErrorResponse());
  }

  @Operation(summary = "Find bills by criteria using PROXY webservice")
  @PostMapping(
      value = "/bills",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<RestApiResponse>> findBills(@RequestBody BillsRequest request) {
    log.info(BILLS_REQUEST_LOG_TAGS, request);
    return proxyService
        .redirectRequestToProxy(request)
        .doOnNext(response -> log.info(BILLS_RESPONSE_LOG_TAGS, response))
        .map(ResponseEntity::ok)
        .switchIfEmpty(buildUserNotFoundErrorResponse());
  }

  /**
   * Discussion: <a
   * href="https://stackoverflow.com/questions/53435140/return-404-when-a-flux-is-empty">when-a-flux-is-empty</a>
   * ResponseStatusException: <a
   * href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/server/ResponseStatusException.html">ResponseStatusException</a>
   */
  private Mono<ResponseEntity<RestApiResponse>> buildUserNotFoundErrorResponse() {
    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
