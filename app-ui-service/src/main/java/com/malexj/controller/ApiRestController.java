package com.malexj.controller;

import com.malexj.model.Bill;
import com.malexj.model.SearchBill;
import com.malexj.model.Subscription;
import com.malexj.model.User;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ApiRestController {

  @Lazy protected final EurekaClient eurekaClient;

  private final Collection<Bill> billList;

  private final WebClient webClient;

  private static final User DEFAULT_USER;

  static {
    DEFAULT_USER = new User();
    DEFAULT_USER.setUsername("Alex");
    DEFAULT_USER.setEmail("alex@dmail.com");
  }

  protected String discoveryServiceUrl() {
    InstanceInfo nextServerFromEureka =
        eurekaClient.getNextServerFromEureka("app-api-gateway", false);
    return nextServerFromEureka.getHomePageUrl();
  }

  @GetMapping("/subscriptions")
  public Mono<ResponseEntity<Subscription>> appSubscriptions() {
    //    return webClient
    //        .get() //
    //        .uri(discoveryServiceUrl() + "/v1/subscriptions") //
    //        .retrieve() //
    //        .bodyToFlux(Subscription.class) //
    //        .collectList() //
    //        .map(ResponseEntity::ok);

    var sub = new Subscription();
    sub.setActive(true);
    sub.setUser(DEFAULT_USER);
    sub.setBills(billList.stream().toList());
    return Mono.just(new ResponseEntity<>(sub, HttpStatus.OK));
  }

  @GetMapping("/bills/{number}")
  public Mono<ResponseEntity<SearchBill>> searchBillByNumber(@PathVariable String number) {
    if (number.isEmpty()) {
      throw new RuntimeException("bill number should be not null or empty!");
    }
    return webClient
        .get() //
        .uri(discoveryServiceUrl() + "/v1/bills/" + number) //
        .retrieve() //
        .bodyToMono(SearchBill.class) //
        .map(ResponseEntity::ok);
  }

  @PostMapping("/bills")
  public Mono<ResponseEntity<Subscription>> subscribe(@RequestBody Subscription subscription) {
    log.info(">>>>> Request: " + subscription);
    //        return webClient.post() //
    //                .uri(discoveryServiceUrl() + "/v1/subscriptions/subscribe") //
    //                .bodyValue(subscription) //
    //                .retrieve() //
    //                .bodyToMono(Subscription.class) //
    //                .map(ResponseEntity::ok);

    billList.addAll(subscription.getBills());
    subscription.setBills(billList.stream().toList());
    return Mono.just(new ResponseEntity<>(subscription, HttpStatus.OK));
  }
}
