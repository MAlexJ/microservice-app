package com.malexj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AppSchedulerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppSchedulerServiceApplication.class, args);
    }

}
