package com.malexj.service;

import com.malexj.mapper.BilDtoMapper;
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

    protected final BilDtoMapper mapper;


    protected String discoveryServiceUrl(String virtualHostname) {
        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka(virtualHostname, false);
        return nextServerFromEureka.getHomePageUrl();
    }


    protected URI buildUri(String url, String endpoint) {
        return UriComponentsBuilder.fromUriString(url) //
                .path(endpoint) //
                .build() //
                .toUri();
    }
}
