package com.malex.services;

import com.malex.models.base.FormUrlencodedData;
import com.malex.models.request.BillRequest;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ApiRestService {

    Mono<String> fetchBillStatus(String url);

    Mono<String> fetchSearchResult(String url, List<FormUrlencodedData> formData);


    Mono<String> fetchSearchResult(MultiValueMap<String, String> formData);

    Mono<String> post(String url, BillRequest jsonRequest);

}
