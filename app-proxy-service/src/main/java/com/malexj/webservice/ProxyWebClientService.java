package com.malexj.webservice;

import com.malexj.model.ResponseState;
import com.malexj.model.proxy.BillStatusesProxyRequest;
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
public class ProxyWebClientService {

  private static final String LOG_REQUEST_MESSAGE = "HTTP Proxy request - {}, url - {}";
  private static final String LOG_RESPONSE_MESSAGE = "HTTP Proxy response - {}";
  private static final String LOG_ERROR_MESSAGE = "Proxy service not available";
  private static final String PROXY_URI_QUERY_PARAM = "secret";

  @Value("${proxy.service.url}")
  private String proxyWebserviceBaseUrl;

  @Value("${proxy.service.endpoint.statuses}")
  private String proxyWebserviceBillStatusesEndpoint;

  @Value("${proxy.service.endpoint.bills}")
  private String proxyWebserviceBillsEndpoint;

  @Value("${proxy.service.secret}")
  private String proxyWebserviceSecret;

  private final WebClient webClient;

  @CircuitBreaker(name = "proxy", fallbackMethod = "proxyFallbackMethod")
  public <T> Mono<ProxyResponse> sendDataToProxy(T request) {
    URI uri =
        (request instanceof BillStatusesProxyRequest)
            ? buildUriComponent(proxyWebserviceBillStatusesEndpoint)
            : buildUriComponent(proxyWebserviceBillsEndpoint);
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

  /** Fallback method for {@link ProxyWebClientService#sendDataToProxy} */
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

  private URI buildUriComponent(String endpoint) {
    return UriComponentsBuilder.fromUriString(proxyWebserviceBaseUrl)
        .pathSegment(endpoint)
        .queryParam(PROXY_URI_QUERY_PARAM, proxyWebserviceSecret)
        .build()
        .toUri();
  }
}
