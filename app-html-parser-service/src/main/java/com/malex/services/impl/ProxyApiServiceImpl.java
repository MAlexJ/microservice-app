package com.malex.services.impl;

import com.malex.models.request.ProxyRequest;
import com.malex.models.response.ProxyResponse;
import com.malex.services.AbstractRestService;
import com.malex.services.ProxyApiService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyApiServiceImpl extends AbstractRestService implements ProxyApiService {

  @Value("${proxy.service.url}")
  private String proxyServiceUrl;

  @Value("${proxy.service.secret}")
  private String proxyServiceSecret;

  private final WebClient webClient;

  @Override
  @CircuitBreaker(name = "proxy", fallbackMethod = "proxyFallbackMethod")
  public Mono<ProxyResponse> makeRequestToProxyServer(String url) {
    URI uri = buildUriComponent(proxyServiceUrl, Map.of("secret", proxyServiceSecret));
    return webClient
        .post()
        .uri(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(ProxyRequest.builder().url(url).build())
        .retrieve()
        .bodyToMono(ProxyResponse.class)
        .doOnNext(
            response -> log.info("Proxy API call url - {}, Http response - {}", uri, response));
  }
}
