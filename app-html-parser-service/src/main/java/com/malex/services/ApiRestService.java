package com.malex.services;

import com.malex.models.base.FormUrlencodedData;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ApiRestService {

    Mono<String> fetchBillStatus(String url);

    Mono<String> fetchSearchResult(String url, List<FormUrlencodedData> formData);

}
