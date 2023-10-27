package com.malexj.controller;


import com.malexj.entity.UserEntity;
import com.malexj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

    private final UserRepository userRepository;

    @GetMapping("/users/{username}")
    public Mono<ResponseEntity<UserEntity>> findUserByName(@PathVariable String username) {
        return userRepository.findUserEntitiesByUsername(username) //
                .doOnNext(user -> log.info("user - {} found by name - {}", user, username)) //
                .map(ResponseEntity::ok);
    }

}
