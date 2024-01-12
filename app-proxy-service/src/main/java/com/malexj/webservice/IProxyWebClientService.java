package com.malexj.webservice;

import com.malexj.model.proxy.ProxyRequest;
import com.malexj.model.proxy.ProxyResponse;
import reactor.core.publisher.Mono;

public interface IProxyWebClientService {

  Mono<ProxyResponse> sendDataToWebserver(ProxyRequest request);
}
