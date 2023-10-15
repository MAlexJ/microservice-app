package com.serjlemast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class AppMailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppMailServiceApplication.class, args);
    }

}
