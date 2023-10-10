package com.malexj.service;

import java.util.concurrent.Callable;

public interface CallableService {

    <T> void execute(Callable<T> task);
}
