package com.malex.controllers.v2;

import com.malex.models.request.HtmlParserRequest;
import com.malex.models.response.HtmlParserResponse;
import com.malex.services.HtmlPageParsingService;
import com.malex.services.ProxyApiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class ApiRestControllerV2 {

  private final ProxyApiService proxyApiService;
  private final HtmlPageParsingService parsingService;

  @Operation(summary = "Proxy service HTML representation")
  @PostMapping("/proxy")
  public Mono<HtmlParserResponse> parseHtml(@RequestBody HtmlParserRequest request) {
    return proxyApiService
        .makeRequestToProxyServer(request.getUrl())
        .flatMap(response -> parsingService.processXPathExpressions(response, request.getXpath()));
  }
}
