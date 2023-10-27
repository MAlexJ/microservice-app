package com.malexj.repository;

import com.malexj.model.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findUserEntitiesByUsernameIgnoreCase(String username);
}
