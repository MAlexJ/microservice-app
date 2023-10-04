package com.malex.services;

import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JsoupService {

    Flux<BillStatus> processBillStatus(String html);

    Flux<Bill> processBillSearchResult(String html);
}
