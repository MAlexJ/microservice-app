package com.malex.services;

import com.malex.models.base.BillStatus;
import reactor.core.publisher.Flux;

public interface JsoupService {

    Flux<BillStatus> processHtmlRequest(String html);
}
