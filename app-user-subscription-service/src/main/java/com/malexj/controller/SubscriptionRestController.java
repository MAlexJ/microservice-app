package com.malexj.controller;

import com.malexj.mapper.ObjectMapper;
import com.malexj.model.dto.EmailDto;
import com.malexj.model.dto.SubscriptionDto;
import com.malexj.model.entity.SubscriptionEntity;
import com.malexj.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionRestController {

    private final ObjectMapper mapper;
    private final SubscriptionRepository repository;


    @GetMapping
    public Flux<SubscriptionEntity> findAllSubscriptions() {
        return repository.findAll();
    }


    @GetMapping("/active")
    public Flux<SubscriptionEntity> findActiveSubscriptions(@RequestParam(value = "page", defaultValue = "0") long page, //
                                                            @RequestParam(value = "size", defaultValue = "10") long size) {
        return repository.findActiveSubscription(true) //
                .skip(page * size) //
                .take(size);
    }


    @PostMapping("/subscribe")
    public Mono<SubscriptionEntity> createSubscription(@RequestBody SubscriptionDto request) {
        SubscriptionEntity subscriptionEntity = mapper.convertDtoToEntity(request);
        return repository.save(subscriptionEntity) //
                .onErrorResume(ex -> {
                    log.error(ex.getMessage());
                    if (ex instanceof DuplicateKeyException) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
                    }
                    return Mono.error(ex);
                });
    }


    @PostMapping("/unsubscribe")
    public Mono<SubscriptionEntity> unsubscribe(@RequestBody EmailDto dto) {
        String email = dto.getEmail();
        return repository.updateSubscription(email) //
                .flatMap(r -> repository.findSubscriptionEntitiesByUser_Email(email));
    }

}