package com.malex.services;

import com.malex.models.base.FormUrlencodedData;
import com.malex.models.request.ProxyRequest;
import com.malex.models.response.ProxyResponse;
import java.util.List;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface ApiRestService {

    Mono<String> fetchBillStatus(String url);

    Mono<String> fetchSearchResult(String url, List<FormUrlencodedData> formData);

    Mono<String> fetchSearchResult(MultiValueMap<String, String> formData);

    Mono<ProxyResponse> fetchProxyResponse(ProxyRequest request);
}
