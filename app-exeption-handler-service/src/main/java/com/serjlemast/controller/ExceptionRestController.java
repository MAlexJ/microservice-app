package com.serjlemast.controller;

import com.serjlemast.entity.ExceptionEntity;
import com.serjlemast.repository.ExceptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ExceptionRestController {

    private final ExceptionRepository exceptionRepository;

    @PostMapping("/handle")
    public ResponseEntity<ExceptionEntity> handleException(@RequestBody ExceptionEntity request) {
        log.info("Start processing handling exception, request - {}", request);
        CompletableFuture.runAsync(() -> request.getMessage());
        log.info("End processing handling");
        return ResponseEntity.status(HttpStatus.CREATED) //
                .body(request);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExceptionEntity>> getExceptions() {
        log.info("Start processing getting all exception");
        List<ExceptionEntity> all = exceptionRepository.findAll();
        log.info("End processing getting");
        return ResponseEntity.status(HttpStatus.CREATED) //
                .body(all);
    }

}
