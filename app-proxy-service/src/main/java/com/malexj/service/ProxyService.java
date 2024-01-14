package com.malexj.service;

import com.malexj.mapper.ObjectMapper;
import com.malexj.model.api.BillStatusRequest;
import com.malexj.model.api.BillsRequest;
import com.malexj.model.api.RestApiResponse;
import com.malexj.webservice.ProxyWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProxyService {
  private final ObjectMapper mapper;
  private final ProxyWebClientService webClientService;

  public <T> Mono<RestApiResponse> redirectRequestToProxy(T request) {
    if (request instanceof BillStatusRequest billStatusRequest) {
      return Mono.fromSupplier(() -> mapper.convertRequest(billStatusRequest))
          .flatMap(webClientService::sendDataToProxy)
          .map(mapper::convertResponse);
    }

    return Mono.fromSupplier(() -> mapper.convertRequest((BillsRequest) request))
        .flatMap(webClientService::sendDataToProxy)
        .map(mapper::convertResponse);
  }
}
