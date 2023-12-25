package com.malex.services;

import com.malex.models.response.ProxyResponse;
import reactor.core.publisher.Mono;

public interface ProxyApiService {

  Mono<ProxyResponse> makeRequestToProxyServer(String url);
}
