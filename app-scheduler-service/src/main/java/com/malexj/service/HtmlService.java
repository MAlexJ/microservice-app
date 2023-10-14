package com.malexj.service;

import com.malexj.model.request.BillRequest;
import com.malexj.model.response.BillResponse;
import com.malexj.model.response.SearchResponse;
import reactor.core.publisher.Mono;

public interface HtmlService {

    Mono<SearchResponse> fetchSearchBill();

    Mono<BillResponse> fetchBillStatuses(BillRequest request);
}
