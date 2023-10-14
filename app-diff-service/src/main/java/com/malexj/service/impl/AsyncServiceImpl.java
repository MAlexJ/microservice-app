package com.malexj.service.impl;

import com.malexj.service.AsyncService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Override
    public <T> void execute(Callable<T> task) {
        Mono.defer(() -> Mono.fromCallable(task)) //
                .subscribeOn(Schedulers.boundedElastic()) //
                .subscribe();
    }
}
