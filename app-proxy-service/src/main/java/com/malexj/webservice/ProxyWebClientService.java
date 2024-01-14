package com.malexj.webservice;

import com.malexj.model.ResponseState;
import com.malexj.model.proxy.ProxyRequest;
import com.malexj.model.proxy.ProxyResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyWebClientService implements IProxyWebClientService {

  private static final String LOG_REQUEST_MESSAGE = "HTTP Proxy request - {}, url - {}";
  private static final String LOG_RESPONSE_MESSAGE = "HTTP Proxy response - {}";
  private static final String LOG_ERROR_MESSAGE = "Proxy service not available";
  private static final String PROXY_URI_QUERY_PARAM = "secret";

  @Value("${proxy.service.url}")
  private String proxyServiceUrl;

  @Value("${proxy.service.endpoint.statuses}")
  private String proxyServiceEndpoint;

  @Value("${proxy.service.secret}")
  private String proxyServiceSecret;

  private final WebClient webClient;

  @Override
  @CircuitBreaker(name = "proxy", fallbackMethod = "proxyFallbackMethod")
  public Mono<ProxyResponse> sendDataToWebserver(ProxyRequest request) {
    URI uri = buildUriComponent();
    log.info(LOG_REQUEST_MESSAGE, request, uri);
    return webClient
        .post()
        .uri(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(ProxyResponse.class)
        .doOnNext(response -> log.info(LOG_RESPONSE_MESSAGE, response));
  }

  /** Fallback method for {@link IProxyWebClientService#sendDataToWebserver} */
  private Mono<ProxyResponse> proxyFallbackMethod(Exception ex) {
    log.error(LOG_ERROR_MESSAGE, ex);
    return Mono.fromSupplier(
        () -> {
          var response = new ProxyResponse();
          response.setState(ResponseState.FALLBACK);
          response.setStatusCode(500);
          response.setStatus("Service not available!");
          return response;
        });
  }

  private URI buildUriComponent() {
    return UriComponentsBuilder.fromUriString(proxyServiceUrl)
        .pathSegment(proxyServiceEndpoint)
        .queryParam(PROXY_URI_QUERY_PARAM, proxyServiceSecret)
        .build()
        .toUri();
  }
}
