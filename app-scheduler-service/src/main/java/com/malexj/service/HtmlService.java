package com.malexj.service;

import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.model.response.SearchResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HtmlService {

    Mono<SearchResponse> fetchBillSearch();

    Mono<BillResponse> fetchBillStatuses(BillRequest request);

    Flux<BillRequest> convertSearchResponse(SearchResponse response);
}
