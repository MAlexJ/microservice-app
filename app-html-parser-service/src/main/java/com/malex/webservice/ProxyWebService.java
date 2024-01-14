package com.malex.webservice;

import com.malex.model.base.ResponseState;
import com.malex.model.proxy.request.BillStatusesProxyRequest;
import com.malex.model.proxy.response.ProxyResponse;
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

  @Value("${proxy-service.endpoint.proxy.bills}")
  private String billsEndpoint;

  @Value("${proxy-service.endpoint.proxy.statuses}")
  private String billStatusesEndpoint;

  @Value("${proxy-service.application.name}")
  private String virtualHostname;

  public <T> Mono<ProxyResponse> fetchBillStatus(T request) {
    URI uri =
        (request instanceof BillStatusesProxyRequest)
            ? buildProxyServiceUri(billStatusesEndpoint)
            : buildProxyServiceUri(billsEndpoint);
    log.info("HTTP Proxy request - {}, uri - {}", request, uri);
    return webClient
        .post()
        .uri(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(ProxyResponse.class)
        .doOnNext(
            response -> {
              String message = "HTTP Proxy state - {}, response - {}";
              if (ResponseState.FALLBACK == response.getState()) {
                log.warn(message, response.getState(), response);
              }
              log.info(message, response.getState(), response);
            });
  }

  private URI buildProxyServiceUri(String path) {
    return UriComponentsBuilder.fromUriString(discoveryServiceUrl(virtualHostname))
        .path(path)
        .build()
        .toUri();
  }

  private String discoveryServiceUrl(String virtualHostname) {
    var instance = eurekaClient.getNextServerFromEureka(virtualHostname, false);
    return instance.getHomePageUrl();
  }
}
