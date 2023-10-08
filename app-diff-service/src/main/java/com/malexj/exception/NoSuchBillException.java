package com.malexj.exception;

public class NoSuchBillException extends RuntimeException{
    public NoSuchBillException() {
        super();
    }

    public NoSuchBillException(String message) {
        super(message);
    }
}
