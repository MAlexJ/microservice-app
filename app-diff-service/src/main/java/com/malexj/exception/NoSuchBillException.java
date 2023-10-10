package com.malexj.exception;

public class NoSuchBillException extends RuntimeException {
    public NoSuchBillException(String message) {
        super(message);
    }
}
