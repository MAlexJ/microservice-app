package com.malex.controllers.v2;

import com.malex.models.request.ProxyRequest;
import com.malex.models.response.ProxyResponse;
import com.malex.services.ApiRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final ApiRestService restService;

    @Value("${proxy.service.url}")
    private String proxyServiceUrl;

    @Value("${proxy.service.secret}")
    private String secret;

    private String proxyUrl = //
            "https://xxxxxxxxxxxxx";

    @PostMapping("/bills")
    public Mono<ProxyResponse> findBillStatuses(@RequestBody ProxyRequest request) {
        return restService.fetchProxyResponse(proxyUrl, request);
    }

}