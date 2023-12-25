package com.malex.controllers.v1;

import com.malex.mapper.ObjectMapper;
import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.models.request.BillRequest;
import com.malex.models.request.SearchRequest;
import com.malex.models.response.BillResponse;
import com.malex.models.response.SearchResponse;
import com.malex.services.ApiRestService;
import com.malex.services.HtmlPageParsingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

  private final ObjectMapper mapper;
  private final ApiRestService restService;
  private final HtmlPageParsingService parsingService;

  /**
   * Json request example
   *
   * <pre>{@code
   * {
   * "link":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664",
   * "number":"9672",
   * "name":"text description",
   * "registrationDate":"2023-09-04"
   * }
   * }</pre>
   */
  @PostMapping("/bills")
  public Mono<BillResponse> findBillStatuses(@RequestBody BillRequest request) {
    log.info("Start processing find bill statuses to search statuses, request - {}", request);
    return restService
        .fetchBillStatus(request.getLink())
        .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service"))
        .flatMapMany(parsingService::processBillStatus)
        .collectList()
        .map(statuses -> buildBillResponse(request, statuses))
        .doOnNext(message -> log.info("Processing completed, response - {}", message));
  }

  @PostMapping("/searchResults")
  public Mono<SearchResponse> findBillsByCriteria(@RequestBody SearchRequest request) {
    log.info("Start processing search bills, request - {}", request);
    return restService
        .fetchSearchResult(request.getLink(), request.getFormUrlencodedData())
        .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service"))
        .flatMapMany(parsingService::processBillSearchResult)
        .collectList()
        .map(this::buildSearchResponse)
        .doOnNext(message -> log.info("Search processing completed, response - {}", message));
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
