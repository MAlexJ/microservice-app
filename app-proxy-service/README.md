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

Note:

1. Authentication - System (use secret). This is where you can choose the authentication method for your function
2. Respond With Result - enable

Function: find bill statuses

```
// http web client
exports = function ({query, headers, body}, response) {
    // 1. parse json body
    const jsonBody = JSON.parse(body.text());
    // 2. extract url
    const url = jsonBody["url"]
    console.log("url:", url);
    // 3. send data to webservice
    return context.http.get({url: url});
};
```

Function: search bills

```
// http web client
exports = function ({query, headers, body}, response) {
    // 1. parse json body
    const jsonBody = JSON.parse(body.text());
    // 2. extract url
    const url = jsonBody["url"]
    console.log("url:", url);
    // 3. extract formUrlencodedData payload
    const data = jsonBody["formUrlencodedData"]
    let formUrlencodedData = data.map(function (val) {
        return val.key + '=' + val.value;
    }).join('&');
    console.log("formUrlencodedData:", formUrlencodedData);
    // 4. send data to webservice
    return context.http.post({
        url: url, body: formUrlencodedData, headers: {"Content-Type": ["application/x-www-form-urlencoded"]},
    }).then(response => {
        console.log("Response:", response['status']);
        return response;
    })
};
```

7. 'Function' -> Save Draft -> Review Draft and Deploy
8. Run API in Postman with JSON payload

Example: find bill statuses

```
URL: https://eu-central-1.aws.data.mongodb-api.com/app/xxxxxxxxxxxx/endpoint/proxy/bills/status
Method: POST
Headers: Content-Type: application/json
Body:
{
   "url":"https://itd.rada.gov.ua/billInfo/Bills/Card/42664"
}
```

Example: search bills

```
URL: https://eu-central-1.aws.data.mongodb-api.com/app/xxxxxxxxxxxx/endpoint/proxy/bills
Method: POST
Headers: Content-Type: application/json
Body:
{
   "url":"https://itd.rada.gov.ua/billInfo/Bills/searchResults",
   "formUrlencodedData":[
      {
         "key":"BillSearchModel.registrationNumber",
         "value":"9672"
      },
      {
         "key":"BillSearchModel.registrationNumberCompareOperation",
         "value":"2"
      }
   ]
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