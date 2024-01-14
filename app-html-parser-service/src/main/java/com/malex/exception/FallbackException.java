package com.malex.exception;

public class FallbackException extends RuntimeException {
    public FallbackException(String message) {
        super(message);
    }
}
