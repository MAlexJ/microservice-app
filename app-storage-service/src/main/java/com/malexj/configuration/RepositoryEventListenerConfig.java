package com.malexj.configuration;

import com.malexj.listener.BillEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryEventListenerConfig {

    @Bean
    BillEventListener bookEventHandler() {
        return new BillEventListener();
    }
}
