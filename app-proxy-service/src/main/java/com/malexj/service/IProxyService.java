package com.malexj.service;

import com.malexj.model.api.RestApiRequest;
import com.malexj.model.api.RestApiResponse;
import reactor.core.publisher.Mono;

public interface IProxyService {
    Mono<RestApiResponse> redirectRequestToProxyWebServer(RestApiRequest request);
}
