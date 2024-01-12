## Proxy service

### API documentation

Project uses OpenAPI (link: https://springdoc.org/) that will describe the API
of REST endpoints.

API documentation should be available by URL: http://localhost:{port}/

### ENV variables:

* SPRING_ACTUATOR_ENABLED - true by default
* APP_PROXY_SERVICE_PROXY_URL - url to web Atlas Mongodb service
* APP_PROXY_SERVICE_PROXY_SECRET - admin secret key

### Atlas Mongodb Web client

Service: https://cloud.mongodb.com/

Tutorial "Build a Totally Serverless REST API with MongoDB Atlas",
link [Build a Totally Serverless REST API with MongoDB Atlas](https://www.youtube.com/watch?v=FkD_tf8vkfg)

#### How to create a web client:

1. 'App Services' -> 'Create a New App'
2. 'Https Endpoints' -> 'Add an Endpoint'
3. in 'Add an Endpoint' create and fill new route
4. in 'Add an Endpoint' -> create '+ New Function'
5. 'Function' -> find new function by name and create js script
6. 'Function Editor' -> configure script

```
// http web client
exports = function({ query, headers, body}, response) {
    const jsonBoby = JSON.parse(body.text());
    const url = jsonBoby["url"]
    console.log("url:", url);
    return context.http.get({ url: url});
};
```

7. 'Function' -> Save Draft -> Review Draft and Deploy
8. Run API in Postman with JSON payload

```
URL: https://eu-central-1.aws.data.mongodb-api.com/app/xxxxxxxxxxxx/endpoint/test
POST
Content-Type: application/json
{
   "link":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664",
   "number":"9672",
   "name":"text description",
   "registrationDate":"2023-09-04"
}
```

#### Monitoring application:

1. App service -> MANAGE section -> Logs

#### Security

1. AUTHORIZATION -> Require Secret
2. Create secret and secret value
3. Add to endpoint

```
curl \
-H "Content-Type: application/json" \
-d '{"json":"json"}' \
https://eu-central.aws.data.mongodb-api.com/endpoint?secret=XXXXXXXXXX
```

4. Edit secret in 'Values' section