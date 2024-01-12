package com.malexj.controller;

import com.malexj.model.api.RestApiRequest;
import com.malexj.model.api.RestApiResponse;
import com.malexj.service.IProxyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

  private static final String LOG_REQUEST_MESSAGE = "HTTP API request - {}";
  private static final String LOG_RESPONSE_MESSAGE = "HTTP API response: {}";

  private final IProxyService service;

  @Operation(summary = "Proxy service HTML representation")
  @PostMapping("/proxy")
  public Mono<ResponseEntity<RestApiResponse>> proxy(@RequestBody RestApiRequest request) {
    log.info(LOG_REQUEST_MESSAGE, request);
    return service
        .redirectRequestToProxyWebServer(request)
        .doOnNext(response -> log.info(LOG_RESPONSE_MESSAGE, response))
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
