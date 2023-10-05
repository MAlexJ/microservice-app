package com.malexj.service.impl;

import com.malexj.model.response.BillResponse;
import com.malexj.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class StorageServiceImpl implements StorageService {

    private static final String STORAGE_SERVICE_URL = "http://localhost:8087/bills";

    private WebClient webClient;

    @Override
    public Mono<BillResponse> findBillByNumber(String number) {
        UriComponents url = UriComponentsBuilder.fromUriString(STORAGE_SERVICE_URL) //
                .queryParam("number", number)
                .build();

        return webClient.get() //
                .uri(url.toUri()) //
                .retrieve()//
                .bodyToMono(BillResponse.class) //
                .doOnNext(response -> log.info("Response " + response));
    }
}
