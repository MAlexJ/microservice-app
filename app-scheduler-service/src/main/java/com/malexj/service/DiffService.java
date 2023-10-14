package com.malexj.service;

import com.malexj.model.response.BillResponse;
import reactor.core.publisher.Flux;

public interface DiffService {

    Flux<String> fetchDiff(Flux<BillResponse> billStatuses);
}
