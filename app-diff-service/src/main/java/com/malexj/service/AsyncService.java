package com.malexj.service;

import java.util.concurrent.Callable;

public interface AsyncService {

    /**
     * CallableService service performs lazy processing without blocking HTTP request
     */
    <T> void execute(Callable<T> task);
}
