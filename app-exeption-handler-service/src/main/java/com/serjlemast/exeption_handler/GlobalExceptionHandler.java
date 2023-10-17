package com.serjlemast.exeption_handler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ComponentScan(basePackages = "com.serjlemast.controller")
@ControllerAdvice(basePackages = "com.serjlemast.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public void handleProductNotFoundException(RuntimeException ex) {
        System.out.println("ERROR");
    }
}
