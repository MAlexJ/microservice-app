package com.malex.services;

import com.malex.models.base.Bill;
import com.malex.models.base.BillStatus;
import com.malex.models.response.HtmlParserResponse;
import com.malex.models.response.ProxyResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HtmlPageParsingService {

  Flux<BillStatus> processBillStatus(String html);

  Flux<Bill> processBillSearchResult(String html);

  Mono<HtmlParserResponse> processXPathExpressions(ProxyResponse response, String xpath);
}
