package com.malexj.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class AbstractService {


    protected URI buildUri(String baseUrl, String endpoint) {
        return buildUriComponents(baseUrl, endpoint).build().toUri();
    }

    protected UriComponentsBuilder buildUriComponents(String baseUrl, String endpoint) {
        return UriComponentsBuilder.fromUriString(baseUrl).path(endpoint);
    }
}
