package com.malexj.service.impl;

import com.malexj.model.request.BillDiffRequest;
import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.service.AbstractService;
import com.malexj.service.StorageService;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class StorageServiceImpl extends AbstractService implements StorageService {

    @Value("${bill-storage-service.application.name}")
    private String virtualHostname;

    @Value("${bill-storage-service.endpoint.bills}")
    private String billsEndpoint;

    @Value("${bill-storage-service.endpoint.bill-statuses}")
    private String billStatusesEndpoint;

    public StorageServiceImpl(EurekaClient eurekaClient, WebClient webClient) {
        super(eurekaClient, webClient);
    }

    @Override
    public Mono<BillResponse> findBillByNumber(String number) {
        return webClient.get() //
                .uri(buildUri(Map.of("number", number))) //
                .retrieve()//
                .bodyToMono(BillResponse.class) //
                .doOnNext(response -> log.info("Bill: " + Optional.ofNullable(response.getEmbedded())));
    }

    @Override
    public Mono<BillResponse> save(BillRequest request) {
        return webClient.post() //
                .uri(buildUri(discoveryServiceUrl(virtualHostname), billsEndpoint)) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(request) //
                .retrieve() //
                .bodyToMono(BillResponse.class) //
                .doOnNext(response -> log.info("Save bill: " + response));
    }

    @Override
    public Flux<String> saveBillStatuses(BillDiffRequest request) {
        return Flux.fromIterable(request.getDiffStatuses()).flatMap(billStatus -> webClient.post() //
                .uri(buildUri(discoveryServiceUrl(virtualHostname), billStatusesEndpoint)) //
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(billStatus) //
                .retrieve() //
                .bodyToMono(String.class) //
                .doOnNext(response -> log.info("Save bill status: " + response)));
    }


    private URI buildUri(Map<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = buildUriComponents(discoveryServiceUrl(virtualHostname), billsEndpoint);
        if (!CollectionUtils.isEmpty(queryParams)) {
            queryParams.forEach(uriBuilder::queryParam);
            uriBuilder.pathSegment();
        }
        return uriBuilder.build().toUri();
    }

}