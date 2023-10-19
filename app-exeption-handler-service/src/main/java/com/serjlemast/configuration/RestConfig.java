package com.serjlemast.configuration;

import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

/**
 * Issue: Return IDs in JSON response from Spring Data REST
 * link: <a href="https://stackoverflow.com/questions/44046659/return-ids-in-json-response-from-spring-data-rest">Return IDs in JSON response from Spring Data REST</a>
 */
@Component
public class RestConfig implements RepositoryRestConfigurer {

}
