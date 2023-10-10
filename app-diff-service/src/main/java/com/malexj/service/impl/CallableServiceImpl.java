package com.malexj.service.impl;

import com.malexj.service.CallableService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;

@Service
public class CallableServiceImpl implements CallableService {

    @Override
    public <T> void execute(Callable<T> task) {
        Mono.defer(() -> Mono.fromCallable(task)) //
                .subscribeOn(Schedulers.boundedElastic()) //
                .subscribe();
    }
}
