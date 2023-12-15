package com.malexj.service;

import com.malexj.exception.SchedulerRoutingException;
import com.malexj.mapper.BilDtoMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractService {

    @Lazy
    protected final EurekaClient eurekaClient;

    protected final WebClient webClient;

    protected final BilDtoMapper mapper;


    protected String discoveryServiceUrl(String virtualHostname) {
        try {
            InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka(virtualHostname, false);
            return nextServerFromEureka.getHomePageUrl();
        } catch (Exception e) {
            throw new SchedulerRoutingException(e);
        }
    }


    protected URI buildUri(String url, String endpoint) {
        return UriComponentsBuilder.fromUriString(url) //
                .path(endpoint) //
                .build() //
                .toUri();
    }
}
