package com.malex.webservice;

import com.malex.models.request.ProxyRequest;
import com.malex.models.response.ProxyResponse;
import com.netflix.discovery.EurekaClient;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyWebService {

  private final WebClient webClient;

  @Lazy private final EurekaClient eurekaClient;

  @Value("${proxy-service.endpoint.proxy}")
  private String endpoint;

  @Value("${proxy-service.application.name}")
  private String virtualHostname;

  public Mono<ProxyResponse> fetchBillStatus(ProxyRequest request) {
    log.info("HTTP Proxy request - {}", request);
    return webClient
        .post()
        .uri(buildProxyServiceUri())
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(ProxyResponse.class)
        .doOnNext(response -> log.info("HTTP Proxy response - {}", response));
  }

  private URI buildProxyServiceUri() {
    return UriComponentsBuilder.fromUriString(discoveryServiceUrl(virtualHostname))
        .path(endpoint)
        .build()
        .toUri();
  }

  private String discoveryServiceUrl(String virtualHostname) {
    var instance = eurekaClient.getNextServerFromEureka(virtualHostname, false);
    return instance.getHomePageUrl();
  }
}
