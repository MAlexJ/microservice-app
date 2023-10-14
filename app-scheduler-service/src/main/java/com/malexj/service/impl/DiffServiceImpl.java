package com.malexj.service.impl;

import com.malexj.mapper.BilDtoMapper;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.service.DiffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DiffServiceImpl implements DiffService {

    /**
     * diff-service REST API
     */
    @Value("${diff-service.base-url}")
    private String diffApiBaseUrl;

    @Value("${diff-service.endpoint}")
    private String diffApiEndpoint;

    private final WebClient webClient;

    private final BilDtoMapper mapper;


    @Override
    public Mono<ResponseEntity<Void>> handleDifferencesBillStatuses(BillResponse response) {
        return webClient.post() //
                .uri(buildUri()) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(buildBody(response)) //
                .retrieve() //
                .toBodilessEntity() //
                .doOnNext(r -> log.info("Processing bill - {} status differences", Optional.ofNullable(response.getNumber())));
    }

    private BillRequest buildBody(BillResponse billResponse) {
        return mapper.responseMapper(billResponse);
    }

    private URI buildUri() {
        return UriComponentsBuilder.fromUriString(diffApiBaseUrl) //
                .path(diffApiEndpoint) //
                .build() //
                .toUri();
    }
}
