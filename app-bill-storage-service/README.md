## Bill storage service

The service provides simple CRUD operations to access tables Bills and their statuses <br>
Relationship: one-to-many

Note: 
DATA REST framework specifics:<br>
entity id is href link:

example POST create bill status:
```java
POST /billStatuses HTTP/1.1
Host: localhost:8087
Location: http://localhost:8087/bills/3
Content-Type: application/json
Content-Length: 125
{
    "data": "2023-09-05",
    "status": "Надано для ознайомленняfghjkl",
    "bill": "http://localhost:8087/bills/13"
}
```
'bill' field is href to bill entity, it's not id in table

### Service configuration

#### Spring boot 3
```java
application port:
APP_BILL_STORAGE_SERVICE_PORT - app-storage-server port
```

#### POSTGRESQL configuration
```java
spring datasource:
APP_BILL_STORAGE_SERVICE_POSTGRESQL_HOSTNAME
APP_BILL_STORAGE_SERVICE_POSTGRESQL_DBNAME
APP_BILL_STORAGE_SERVICE_POSTGRESQL_USER
APP_BILL_STORAGE_SERVICE_POSTGRESQL_PASSWORD
```

### Info

#### Spring Data REST

1. Issue with POST nested entities (OneToMany)
How to POST nested entities with Spring Data REST
link: https://stackoverflow.com/questions/24569399/how-to-post-nested-entities-with-spring-data-rest


#### QueryQSL (Web Support)
link: https://www.baeldung.com/rest-api-search-querydsl-web-in-spring-data-jpa

Steps:
1. Rest controller or Spring Data rest impl
```java
@RequestMapping(method = RequestMethod.GET, value = "/users")
```
2. Entity USER with @QueryEntity annotation

3. Repository
```java
@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long>,  //
     QuerydslPredicateExecutor<User>, //
     QuerydslBinderCustomizer<QUser> { }
```
4. APi request with params: ex: firstName
GET: http://localhost:8080/users?firstName=john
And here’s how a potential response would be structure:
```javascript
[
  {
   "id":1,
   "firstName":"john",
   "lastName":"doe",
   "email":"john@test.com",
   "age":11
  }
]
```

#### Liquibase

Link: https://auth0.com/blog/integrating-spring-data-jpa-postgresql-liquibase/ <br>

Managing the Database Schema with Liquibase
1. apply all Liquibase changesets available in a specific folder
   To do that let's create a master Liquibase file called `db.changelog-master.yaml`
    in the `src/main/resources/db/changelog/` folder. <br>

```java 
databaseChangeLog:
        - logicalFilePath: db/changelog/db.changelog-master.yaml
```

example of changeset:
```java
databaseChangeLog:
  - logicalFilePath: db/changelog/db.changelog-master.yaml
  - changeSet:
      id: 1
      author: malexj
      changes:
        - createTable:
            tableName: bill
            columns:
              - column:
                  name: id
                  type: BIGINT
                  autoIncrement: true
                  constraints:
                    primaryKey: true
                    nullable: true
      rollback:
        - delete:
            tableName: bill
```

#### Hibernate One to Many
link: https://www.baeldung.com/hibernate-one-to-many


## Docker

Info: https://hub.docker.com/_/postgres

**database version**: 16
**command**: docker pull postgres:16

start a postgres instance:

```
    $ docker run -d \
    --name postgres-v16-bill-db \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_USER=user \
    -e POSTGRES_DB=db \
    postgres:16
```

one-line command

```
docker run -d -p 5433:5432 --name postgres-v16-bill-db -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=db postgres:16
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