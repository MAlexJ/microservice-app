package com.malex.services;

import com.malex.models.base.ResponseState;
import com.malex.models.response.ProxyResponse;
import com.malex.services.impl.ProxyApiServiceImpl;
import java.net.URI;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class AbstractRestService {

  /** Fallback method for {@link ProxyApiServiceImpl#makeRequestToProxyServer} */
  protected Mono<ProxyResponse> proxyFallbackMethod(Exception ex) {
    log.error("Proxy service not available", ex);
    return Mono.fromSupplier(
        () -> {
          var response = new ProxyResponse();
          response.setState(ResponseState.FALLBACK);
          response.setStatusCode(500);
          response.setStatus("Service not available!");
          return response;
        });
  }

  protected URI buildUriComponent(String url, Map<String, String> params) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(url);
    params.forEach(uriBuilder::queryParam);
    return uriBuilder.build().toUri();
  }
}
