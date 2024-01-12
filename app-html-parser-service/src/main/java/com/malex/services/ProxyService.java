package com.malex.services;

import com.malex.mapper.ObjectMapper;
import com.malex.models.request.BillRequest;
import com.malex.models.response.ProxyResponse;
import com.malex.webservice.ProxyWebService;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProxyService {

  private final ObjectMapper mapper;

  private final ProxyWebService webService;

  public Mono<String> fetchProxyRequest(BillRequest request) {
    return webService
        .fetchBillStatus(mapper.convertBillRequestToProxyRequest(request))
        .map(this::decodeBase64ToHtml);
  }

  private String decodeBase64ToHtml(ProxyResponse response) {
    return Optional.ofNullable(response.getBody())
        .map(ProxyResponse.ProxyBody::getData)
        .map(data -> Base64.getDecoder().decode(data))
        .map(String::new)
        .orElseThrow();
  }
}
