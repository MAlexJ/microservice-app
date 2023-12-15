package com.malexj.exception;

public class SchedulerRoutingException extends RuntimeException {
    public SchedulerRoutingException(Throwable cause) {
        super(cause);
    }

    public SchedulerRoutingException(String message) {
        super(message);
    }
}
