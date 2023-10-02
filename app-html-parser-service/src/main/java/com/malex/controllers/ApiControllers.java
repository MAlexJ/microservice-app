package com.malex.controllers;

import com.malex.models.base.BillStatus;
import com.malex.models.base.FormUrlencodedData;
import com.malex.models.request.BillRequest;
import com.malex.models.request.SearchRequest;
import com.malex.models.response.BillResponse;
import com.malex.services.JsoupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class ApiControllers {

    private WebClient webClient;
    private JsoupService jsoupService;


    @PostMapping("/bills")
    public Mono<BillResponse> findBill(@RequestBody BillRequest request) {
        log.info("Start processing HTTP request - {}", request);
        return apiGetRequest(request.getLink()) //
                .doOnNext(message -> log.info("Processing of HTML page by Jsoup service")) //
                .flatMapMany(html -> jsoupService.processHtmlRequest(html)) //
                .collectList() //
                .map(statuses -> buildBillResponse(request, statuses)) //
                .doOnNext(message -> log.info("HTTP response processing completed, response - {}", message));
    }


    @PostMapping("/searchResults")
    public Mono<String> searchResults(@RequestBody SearchRequest request) {
        Mono<String> retrieve = apiPostRequest(request);
        // impl
        return retrieve;
    }

    private Mono<String> apiPostRequest(SearchRequest request) {
        return webClient.post() //
                .uri(request.getLink()) //
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) //
                .body(BodyInserters.fromFormData(buildPostFormData(request.getFormUrlencodedData()))) //
                .retrieve() //
                .bodyToMono(String.class);
    }

    private static MultiValueMap<String, String> buildPostFormData(List<FormUrlencodedData> dataList) {
        Map<String, List<String>> formUrlencodedData = dataList.stream() //
                .collect(Collectors.toMap(FormUrlencodedData::getKey, formData -> List.of(formData.getValue())));
        return new LinkedMultiValueMap<>(formUrlencodedData);
    }


    private Mono<String> apiGetRequest(String url) {
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