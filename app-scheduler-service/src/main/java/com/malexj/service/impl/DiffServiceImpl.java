package com.malexj.service.impl;

import com.malexj.mapper.BilDtoMapper;
import com.malexj.model.response.BillResponse;
import com.malexj.service.DiffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;


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
    public Flux<String> fetchDiff(Flux<BillResponse> billStatuses) {
        return billStatuses.flatMap(billResponse ->  //
                webClient.post() //
                        .uri(UriComponentsBuilder.fromUriString(diffApiBaseUrl).path(diffApiEndpoint).build().toUri()) //
                        .contentType(MediaType.APPLICATION_JSON) //
                        .bodyValue(mapper.responseMapper(billResponse)) //
                        .retrieve() //
                        .bodyToMono(String.class) //
                        .doOnNext(response -> log.info("Diff service - {}", response)));
    }
}
