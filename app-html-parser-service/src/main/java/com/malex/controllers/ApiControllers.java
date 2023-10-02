package com.malex.controllers;

import com.malex.models.request.BillRequest;
import com.malex.models.response.BillResponse;
import com.malex.models.base.BillStatus;
import com.malex.models.request.SearchRequest;
import com.malex.services.JsoupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class ApiControllers {

    private WebClient webClient;
    private JsoupService jsoupService;


    /**
     * Json request:
     * {
     * "link":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664",
     * "number":"9672",
     * "name": "Проект Закону про внесення змін до статті 23 Закону України \"Про мобілізаційну підготовку та мобілізацію\"",
     * "registrationDate":"2023-09-04"
     * }
     */
    @PostMapping("/bills")
    public Mono<BillResponse> findBill(@RequestBody BillRequest request) {
        log.info("Start processing HTTP request - {}", request);
        return apiRequestByUrl(request.getLink()) //
                .doOnNext(message -> log.info("Processing of HTML page by Jsoup service")) //
                .flatMapMany(html -> jsoupService.processHtmlRequest(html)) //
                .collectList() //
                .map(statuses -> buildBillResponse(request, statuses)) //
                .doOnNext(message -> log.info("HTTP response processing completed, response - {}", message));
    }


    @PostMapping("/searchResults")
    public Mono<String> searchResults(@RequestBody SearchRequest request) {


        return null;
    }


    private Mono<String> apiRequestByUrl(String url) {
        return webClient.get() //
                .uri(url) //
                .retrieve() //
                .bodyToMono(String.class);
    }


    private BillResponse buildBillResponse(BillRequest request, List<BillStatus> statuses) {
        return BillResponse.builder() //
                .link(request.getLink()) //
                .name(request.getName()) //
                .number(request.getNumber()) //
                .registrationDate(request.getRegistrationDate()) //
                .statuses(statuses) //
                .build();
    }

}