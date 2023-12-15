package com.malexj.service.impl;

import com.malexj.mapper.BilDtoMapper;
import com.malexj.model.request.UserSubscriptionRequest;
import com.malexj.service.AbstractService;
import com.malexj.service.UserSubscriptionService;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class UserSubscriptionServiceImpl extends AbstractService implements UserSubscriptionService {

    @Value("${user-subscription-service.application.name}")
    private String virtualHostname;

    @Value("${user-subscription-service.endpoint.subscriptions.active}")
    private String activeSubscriptionsEndpoint;

    public UserSubscriptionServiceImpl(EurekaClient eurekaClient, WebClient webClient, BilDtoMapper mapper) {
        super(eurekaClient, webClient, mapper);
    }

    @Override
    public Flux<UserSubscriptionRequest> fetchUserSubscriptions() {
        return webClient.get() //
                .uri(buildServiceUri(virtualHostname, activeSubscriptionsEndpoint)) //
                .retrieve() //
                .bodyToFlux(UserSubscriptionRequest.class) //
                .doOnNext(response -> log.info("Subscriptions: {}", response));
    }
}