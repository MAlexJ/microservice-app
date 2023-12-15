## Html parser service

### API documentation

Project uses OpenAPI (link: https://springdoc.org/) that will describe the API
of REST endpoints.

API documentation should be available by URL:

* localhost:{PORT}/webjars/swagger-ui/index.html
* localhost:{PORT}/swagger-ui.html

ENV variables:

* APP_HTML_SERVICE_PORT - random port by default
* APP_HTML_SERVICE_SEARCH_BILL_URL - https://itd.rada.gov.ua/billInfo/Bills/searchResults by default
* SPRING_ACTUATOR_ENABLED - true by default 
