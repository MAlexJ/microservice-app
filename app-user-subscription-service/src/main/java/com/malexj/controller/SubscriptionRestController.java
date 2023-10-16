package com.malexj.controller;

import com.malexj.mapper.ObjectMapper;
import com.malexj.model.api.SubscriptionRequest;
import com.malexj.model.entity.Subscription;
import com.malexj.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/subs")
@RequiredArgsConstructor
public class SubscriptionRestController {

    private final ObjectMapper mapper;
    private final SubscriptionRepository repository;


    @GetMapping
    public Flux<Subscription> getSubscription() {
        return repository.findAll();
    }


    @PostMapping
    public Mono<Subscription> save(@RequestBody SubscriptionRequest request) {
        Subscription subscription = mapper.convertToEntity(request);
        return repository.save(subscription)
                .onErrorResume(ex -> {
                    if (ex instanceof DuplicateKeyException) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
                    }
                    return Mono.error(ex);
                });
    }

}