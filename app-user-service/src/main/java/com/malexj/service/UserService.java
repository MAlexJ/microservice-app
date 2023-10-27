package com.malexj.service;

import com.malexj.model.dto.request.UserRequest;
import com.malexj.model.dto.response.UserResponse;
import com.malexj.model.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findUserCredentials(String username);

    Mono<UserResponse> createUser(UserRequest request);
}
