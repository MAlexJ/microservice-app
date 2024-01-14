package com.malex.service;

import com.malex.mapper.ObjectMapper;
import com.malex.model.api.request.BillStatusesRequest;
import com.malex.model.api.request.BillsRequest;
import com.malex.model.proxy.response.ProxyResponse;
import com.malex.webservice.ProxyWebService;
import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyService {

  private final ProxyWebService webService;
  private final ObjectMapper mapper;

  public <T> Mono<String> redirectRequestToProxyWebservice(T request) {
    if (request instanceof BillStatusesRequest billStatusesRequest) {
      return webService
          .fetchBillStatus(mapper.convertBillRequestToProxyRequest(billStatusesRequest))
          .map(this::decodeBase64ToHtml);
    }
    return webService
        .fetchBillStatus(mapper.convertBillsRequestToProxyRequest((BillsRequest) request))
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
