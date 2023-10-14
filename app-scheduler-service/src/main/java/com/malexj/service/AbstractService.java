package com.malexj.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class AbstractService {

    protected URI buildUri(String url, String endpoint) {
        return UriComponentsBuilder.fromUriString(url)
                .path(endpoint)
                .build()
                .toUri();
    }
}
