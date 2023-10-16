package com.malexj.configuration;

import com.malexj.entity.Bill;
import com.malexj.entity.BillStatus;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Issue: Return IDs in JSON response from Spring Data REST
 * link: <a href="https://stackoverflow.com/questions/44046659/return-ids-in-json-response-from-spring-data-rest">Return IDs in JSON response from Spring Data REST</a>
 */
@Component
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Bill.class, BillStatus.class);
    }
}
