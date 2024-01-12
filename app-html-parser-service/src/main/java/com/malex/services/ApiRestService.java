package com.malex.services;

import com.malex.models.base.FormUrlencodedData;
import java.util.List;
import reactor.core.publisher.Mono;

public interface ApiRestService {

  Mono<String> fetchBillStatus(String url);

  Mono<String> fetchSearchResult(String url, List<FormUrlencodedData> formData);
}
