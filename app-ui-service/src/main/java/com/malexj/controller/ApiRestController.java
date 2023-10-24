package com.malexj.controller;

import com.malexj.model.SearchBill;
import com.malexj.model.Subscription;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ApiRestController {

    @Lazy
    protected final EurekaClient eurekaClient;

    protected final WebClient webClient;

    protected String discoveryServiceUrl() {
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka("app-api-gateway", false);
        return nextServerFromEureka.getHomePageUrl();
    }

    @GetMapping("/subscriptions")
    public Mono<ResponseEntity<List<Subscription>>> appSubscriptions() {
        return webClient.get() //
                .uri(discoveryServiceUrl() + "/v1/subscriptions") //
                .retrieve() //
                .bodyToFlux(Subscription.class)  //
                .collectList() //
                .map(ResponseEntity::ok);
    }


    @GetMapping("/bills/{number}")
    public Mono<ResponseEntity<SearchBill>> searchBillByNumber(@PathVariable String number) {
        if (number.isEmpty()) {
            throw new RuntimeException("bill number should be not null or empty!");
        }
        return webClient.get() //
                .uri(discoveryServiceUrl() + "/v1/bills/" + number) //
                .retrieve() //
                .bodyToMono(SearchBill.class) //
                .map(ResponseEntity::ok);
    }

    @PostMapping("/bills")
    public Mono<ResponseEntity<Subscription>> subscribe(@RequestBody Subscription subscription) {
        log.info(">>>>> Request: " + subscription);
        return webClient.post() //
                .uri(discoveryServiceUrl() + "/v1/subscriptions/subscribe") //
                .bodyValue(subscription) //
                .retrieve() //
                .bodyToMono(Subscription.class) //
                .map(ResponseEntity::ok);
    }

}