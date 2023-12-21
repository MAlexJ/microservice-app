package com.malex.controllers.v2;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.malex.models.request.BillRequest;
import com.malex.services.ApiRestService;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.RequiredArgsConstructor;
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


    private final ApiRestService restService;

    @PostMapping("/bills")
    public Mono<String> findBillStatuses(@RequestBody BillRequest request) {
        var url = "https://eu-central-1.aws.data.mongodb-api.com/.....................";
        return restService.post(url, request);
    }

}
