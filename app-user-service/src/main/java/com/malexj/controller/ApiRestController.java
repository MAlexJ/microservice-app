package com.malexj.controller;


import com.malexj.model.dto.request.UserRequest;
import com.malexj.model.dto.response.UserResponse;
import com.malexj.model.entity.User;
import com.malexj.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ApiRestController {

    private final UserService service;

    @GetMapping("/users/{username}")
    public Mono<ResponseEntity<User>> findUserByName(@PathVariable String username) {
        return service.findUserCredentials(username) //
                .doOnNext(user -> log.info("user - {} found by name - {}", user, username)) //
                .map(ResponseEntity::ok);
    }


    @PostMapping("/users")
    public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody UserRequest request) {
        Mono<UserResponse> response = service.createUser(request);
        return response.map(ResponseEntity::ok);
    }

}