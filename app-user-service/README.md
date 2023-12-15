## User service

Service provides API storage and information about users.

Service uses **Postgresql** database and **r2dbc** reactive protocol

## ENV variables:

```
    APP_USER_SERVICE_PORT - random port by default
    SPRING_ACTUATOR_ENABLED - true by default
    APP_USER_POSTGRESQL_HOSTNAME
    APP_USER_SERVICE_POSTGRESQL_DBNAME
    APP_USER_SERVICE_POSTGRESQL_USER
    APP_USER_SERVICE_POSTGRESQL_PASSWORD
```

example for postgres docker:

```
    #app-user-service
    APP_USER_POSTGRESQL_HOSTNAME=localhost
    APP_USER_SERVICE_POSTGRESQL_DBNAME=db
    APP_USER_SERVICE_POSTGRESQL_USER=user
    APP_USER_SERVICE_POSTGRESQL_PASSWORD=password
```

## Liquibase

liquibase use jdbc protocol

changelog in resources:

```
db.changelog-master.yaml
```

## Docker

Info: https://hub.docker.com/_/postgres

**database version**: 16
**command**: docker pull postgres:16

start a postgres instance:

```
    $ docker run -d \
    --name postgres-v16-db \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_USER=user \
    -e POSTGRES_DB=db \
    postgres:16
```

one-line command

```
docker run -d -p 5432:5432 --name postgres-v16-db -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=db postgres:16
```

Note:
The default postgres user and database are created in the entrypoint with initdb.

### API documentation

Project uses OpenAPI (link: https://springdoc.org/) that will describe the API
of REST endpoints.

API documentation should be available by URL:

* localhost:{PORT}/
* localhost:{PORT}/webjars/swagger-ui/index.html
* localhost:{PORT}/swagger-ui.html
