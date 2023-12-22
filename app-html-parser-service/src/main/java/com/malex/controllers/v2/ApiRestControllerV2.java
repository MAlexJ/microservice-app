package com.malex.controllers.v2;

import com.malex.models.request.ProxyRequest;
import com.malex.models.response.ProxyResponse;
import com.malex.services.ApiRestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class ApiRestControllerV2 {

  private final ApiRestService restService;

  @Operation(summary = "Call Proxy HTTP service to get HTML representation specified in json value")
  @PostMapping("/proxy")
  public Mono<ProxyResponse> callProxyService(
      @RequestBody(
              description = "JSON representation",
              required = true,
              content =
                  @Content(
                      schema = @Schema(implementation = ProxyRequest.class),
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      examples = {
                        @ExampleObject(
                            name = "An example request with site url",
                            value = "url",
                            summary =
                                "Url address to web site for which you need to get HTML representation")
                      }))
          @org.springframework.web.bind.annotation.RequestBody
          ProxyRequest request) {
    return restService.fetchProxyResponse(request);
  }
}
