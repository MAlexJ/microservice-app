package com.malexj.service;

import com.malexj.mapper.ObjectMapper;
import com.malexj.model.api.RestApiRequest;
import com.malexj.model.api.RestApiResponse;
import com.malexj.webservice.IProxyWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyService implements IProxyService {
  private final ObjectMapper mapper;
  private final IProxyWebClientService webClientService;

  @Override
  public Mono<RestApiResponse> redirectRequestToProxyWebServer(RestApiRequest request) {
    return Mono.fromSupplier(() -> mapper.convertRequest(request))
        .flatMap(webClientService::sendDataToWebserver)
        .map(mapper::convertResponse);
  }
}
