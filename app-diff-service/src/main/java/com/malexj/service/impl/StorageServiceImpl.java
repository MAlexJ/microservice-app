package com.malexj.service.impl;

import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    @Value("${storage-service.url}")
    private String storageServiceUrl;

    @Value("${storage-service.path.find-bills}")
    private String findBillsPath;

    private final WebClient webClient;

    @Override
    public Mono<BillResponse> findBillByNumber(String number) {
        return webClient.get() //
                .uri(buildUri(Map.of("number", number))) //
                .retrieve()//
                .bodyToMono(BillResponse.class) //
                .doOnNext(response -> log.info("Response " + response));
    }

    @Override
    public Mono<BillResponse> save(BillRequest request) {
        return webClient.post() //
                .uri(buildUri()) //
                .contentType(MediaType.APPLICATION_JSON).bodyValue(request) //
                .retrieve() //
                .bodyToMono(BillResponse.class) //
                .doOnNext(response -> log.info("Response " + response));
    }

    private URI buildUri() {
        return buildUriComponents() //
                .build() //
                .toUri();
    }


    private URI buildUri(Map<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = buildUriComponents();
        if (!CollectionUtils.isEmpty(queryParams)) {
            queryParams.forEach(uriBuilder::queryParam);
            uriBuilder.pathSegment();
        }
        return uriBuilder.build() //
                .toUri();
    }


    private UriComponentsBuilder buildUriComponents() {
        return UriComponentsBuilder.fromUriString(storageServiceUrl) //
                .path(findBillsPath);
    }
}