package com.malexj.service;

import com.malexj.model.response.BillResponse;
import com.malexj.model.response.SearchResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HtmlService {

    Mono<SearchResponse> fetchSearchBill();


    Flux<BillResponse> fetchBillStatuses(Mono<SearchResponse> response);
}
