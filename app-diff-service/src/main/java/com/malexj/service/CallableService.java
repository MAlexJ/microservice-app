package com.malexj.service;

import java.util.concurrent.Callable;

public interface CallableService {

    /**
     * CallableService service performs lazy processing without blocking HTTP request
     */
    <T> void execute(Callable<T> task);
}
