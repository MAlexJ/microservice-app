package com.malexj.controller;

import com.malexj.model.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

public abstract class AbstractRestController {

    /**
     * Discussion: <a href="https://stackoverflow.com/questions/53435140/return-404-when-a-flux-is-empty">when-a-flux-is-empty</a>
     * ResponseStatusException: <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/server/ResponseStatusException.html">ResponseStatusException</a>
     */
    protected Mono<ResponseEntity<User>> buildUserNotFoundErrorResponse() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
