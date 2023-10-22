package com.malex.services;

import com.malex.models.base.FormUrlencodedData;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ApiRestService {

    Mono<String> fetchBillStatus(String url);

    Mono<String> fetchSearchResult(String url, List<FormUrlencodedData> formData);


    Mono<String> fetchSearchResult(MultiValueMap<String, String> formData);

}
