package com.malexj.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
public abstract class AbstractService {

    @Lazy
    protected final EurekaClient eurekaClient;

    protected final WebClient webClient;

    protected String discoveryServiceUrl(String virtualHostname) {
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka(virtualHostname, false);
        return nextServerFromEureka.getHomePageUrl();
    }

    protected URI buildUri(String baseUrl, String endpoint) {
        return buildUriComponents(baseUrl, endpoint).build().toUri();
    }

    protected UriComponentsBuilder buildUriComponents(String baseUrl, String endpoint) {
        return UriComponentsBuilder.fromUriString(baseUrl).path(endpoint);
    }
}
