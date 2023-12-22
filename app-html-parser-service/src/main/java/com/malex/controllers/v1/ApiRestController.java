package com.malex.controllers.v1;

import com.malex.mapper.ObjectMapper;
import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.models.base.FormData;
import com.malex.models.request.BillRequest;
import com.malex.models.request.SearchRequest;
import com.malex.models.response.BillResponse;
import com.malex.models.response.SearchResponse;
import com.malex.services.ApiRestService;
import com.malex.services.HtmlPageParsingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

    private final ObjectMapper mapper;
    private final ApiRestService restService;
    private final HtmlPageParsingService parsingService;


    /**
     * FORM_URLENCODED_DATA:
     * "BillSearchModel.registrationNumber", "9672", //
     * "BillSearchModel.registrationNumberCompareOperation", "2", //
     * "BillSearchModel.session", "10", //
     * "BillSearchModel.registrationRangeStart", "", //
     * "BillSearchModel.registrationRangeEnd", "", //
     * "BillSearchModel.name", "", //
     * "BillSearchModel.detailView", "false" //
     *
     * @param number     - BillSearchModel.registrationNumber
     * @param operation  - BillSearchModel.registrationNumberCompareOperation by default - 2
     * @param session    - BillSearchModel.session by default - 10
     * @param rangeStart - registrationRangeStart empty by default
     * @param rangeEnd   - BillSearchModel.registrationRangeEnd empty by default
     * @param name       - BillSearchModel.name
     * @param detail     - BillSearchModel.detailView by default - false
     * @return {@link SearchResponse} response
     */
    @GetMapping("/bills/{number}")
    public Mono<SearchResponse> findBillByNumber(@PathVariable("number") String number, //
                                                 @RequestParam(value = "operation", defaultValue = "2") String operation, //
                                                 @RequestParam(value = "session", defaultValue = "10") String session, //
                                                 @RequestParam(value = "rangeStart", defaultValue = "") String rangeStart, //
                                                 @RequestParam(value = "rangeEnd", defaultValue = "") String rangeEnd, //
                                                 @RequestParam(value = "name", defaultValue = "") String name, //
                                                 @RequestParam(value = "detail", defaultValue = "false") String detail) {
        FormData formData = FormData.builder() //
                .registrationNumber(number) //
                .registrationNumberCompareOperation(operation) //
                .session(session) //
                .registrationRangeStart(rangeStart) //
                .registrationRangeEnd(rangeEnd) //
                .name(name) //
                .detailView(detail) //
                .build();
        return restService.fetchSearchResult(formData.getFormData()) //
                .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service")) //
                .flatMapMany(parsingService::processBillSearchResult) //
                .collectList() //
                .map(this::buildSearchResponse) //
                .doOnNext(message -> log.info("Search API response processing completed, response - {}", message));
    }


    /**
     * Json request example
     * <pre>
     * {@code
     * {
     * "link":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664",
     * "number":"9672",
     * "name":"text description",
     * "registrationDate":"2023-09-04"
     * }
     * }
     * </pre>
     */
    @PostMapping("/bills")
    public Mono<BillResponse> findBillStatuses(@RequestBody BillRequest request) {
        log.info("Start processing find bill statuses to search statuses and parse HTML page, request - {}", request);
        return restService.fetchBillStatus(request.getLink()) //
                .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service")) //
                .flatMapMany(parsingService::processBillStatus) //
                .collectList() //
                .map(statuses -> buildBillResponse(request, statuses)) //
                .doOnNext(message -> log.info("Parse API response processing completed, response - {}", message));
    }


    @PostMapping("/searchResults")
    public Mono<SearchResponse> findBillsByCriteria(@RequestBody SearchRequest request) {
        log.info("Start processing find bills and parse HTML page, request - {}", request);
        return restService.fetchSearchResult(request.getLink(), request.getFormUrlencodedData()) //
                .doOnNext(message -> log.info("Processing html page with Html/Jsoup parsing service")) //
                .flatMapMany(parsingService::processBillSearchResult) //
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