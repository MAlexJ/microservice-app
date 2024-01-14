package com.malex.controller;

import com.malex.exception.FallbackException;
import com.malex.jsoup.HtmlPageParsingService;
import com.malex.mapper.ObjectMapper;
import com.malex.model.api.request.BillStatusesRequest;
import com.malex.model.api.response.BillStatusesResponse;
import com.malex.model.api.response.BillsResponse;
import com.malex.model.base.Bill;
import com.malex.model.base.BillStatus;
import com.malex.service.ProxyService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class AbstractRestApiController {

  protected final HtmlPageParsingService parsingService;
  protected final ProxyService proxyService;
  private final ObjectMapper mapper;

  protected ResponseEntity<BillStatusesResponse> buildSuccessfulResponseEntity(
      BillStatusesRequest request, List<BillStatus> statuses) {
    return new ResponseEntity<>(buildResponse(request, statuses), HttpStatus.OK);
  }

  protected BillStatusesResponse buildFallbackResponse(BillStatusesRequest request) {
    return buildResponse(request, Collections.emptyList());
  }

  protected BillsResponse buildFallbackResponse() {
    return new BillsResponse(Collections.emptyList());
  }

  private BillStatusesResponse buildResponse(
      BillStatusesRequest request, List<BillStatus> statuses) {
    return mapper.convertToResponse(request).fetchStatuses(statuses);
  }

  protected ResponseEntity<BillsResponse> buildSuccessfulResponse(List<Bill> bills) {
    return new ResponseEntity<>(new BillsResponse(bills), HttpStatus.OK);
  }

  /** Handle fallback/errors proxy webservice response */
  protected <T> Mono<ResponseEntity<T>> handleFallbackResponse(Throwable throwable, T response) {
    if (throwable instanceof FallbackException) {
      return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }
    return buildInternalServerErrorResponse();
  }

  /**
   * Discussion: <a
   * href="https://stackoverflow.com/questions/53435140/return-404-when-a-flux-is-empty">when-a-flux-is-empty</a>
   *
   * <p>Discussion ResponseStatusException: <a
   * href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/server/ResponseStatusException.html">ResponseStatusException</a>
   */
  protected <T> Mono<ResponseEntity<T>> buildInternalServerErrorResponse() {
    return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
  }
}
