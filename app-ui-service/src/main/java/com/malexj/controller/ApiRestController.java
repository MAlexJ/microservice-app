package com.malexj.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<String>> appSubscriptions() {
        String resp = "[\n" + "    {\n" + "        \"id\": \"653189d3f5e5120cdb241561\",\n" + "        \"user\": {\n" + "            \"username\": \"malex\",\n" + "            \"email\": \"hsh@mz.ck2k\"\n" + "        },\n" + "        \"bills\": null,\n" + "        \"createdDate\": null,\n" + "        \"active\": true\n" + "    },\n" + "    {\n" + "        \"id\": \"65318a7df5e5120cdb241562\",\n" + "        \"user\": {\n" + "            \"username\": \"malex\",\n" + "            \"email\": \"hsh@mz.dssddsdssd\"\n" + "        },\n" + "        \"bills\": null,\n" + "        \"createdDate\": null,\n" + "        \"active\": true\n" + "    }\n" + "]";
        return Mono.just(ResponseEntity.ok(resp));
    }


    @GetMapping("/bills/{number}")
    public Mono<ResponseEntity<String>> searchBillByNumber(@PathVariable String number) {
        if (number.isEmpty()) {
            throw new RuntimeException("bill number should be not null or empty!");
        }
        Mono<String> request = webClient.get() //
                .uri(discoveryServiceUrl()) //
                .retrieve() //
                .bodyToMono(String.class);
        return request.map(ResponseEntity::ok);
    }

    @PostMapping("/bills")
    public Mono<ResponseEntity<String>> subscribe(@RequestBody String bills) {
        log.info(">>>>> Request: " + bills);
        return Mono.empty();
    }

}