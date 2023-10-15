package com.malexj.service.impl;

import com.malexj.mapper.BilDtoMapper;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.service.AbstractService;
import com.malexj.service.DiffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DiffServiceImpl extends AbstractService implements DiffService {

    /**
     * diff-service REST API
     */
    @Value("${diff-service.base-url}")
    private String baseUrl;

    @Value("${diff-service.endpoint}")
    private String endpoint;

    private final WebClient webClient;

    private final BilDtoMapper mapper;


    @Override
    public Mono<ResponseEntity<Void>> processingBillStatusDifferences(BillResponse response) {
        return webClient.post() //
                .uri(buildUri(baseUrl, endpoint)) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(buildBody(response)) //
                .retrieve() //
                .toBodilessEntity() //
                .doOnNext(r -> log.info("Processing bill - {} status differences", Optional.ofNullable(response.getNumber())));
    }

    private BillRequest buildBody(BillResponse billResponse) {
        return mapper.responseMapper(billResponse);
    }
}
