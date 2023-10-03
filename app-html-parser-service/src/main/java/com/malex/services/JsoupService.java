package com.malex.services;

import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JsoupService {

    Flux<BillStatus> processHtmlRequest(String html);

    Flux<Bill> processSearchResult(String html);
}
