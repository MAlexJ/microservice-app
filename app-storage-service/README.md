## Storage service

Description: <br>
1. Add ```.env``` file in root directory
2. Add ```env_variables```:

### POSTGRESQL configuration
```java
- APP_STORAGE_POSTGRESQL_HOSTNAME=
- APP_STORAGE_POSTGRESQL_DBNAME=
- APP_STORAGE_POSTGRESQL_USER=
- APP_STORAGE_CLOUD_KAFKA_PASSWORD=
```

### Spring Data REST

1. Issue with POST nested entities (OneToMany)
How to POST nested entities with Spring Data REST
link: https://stackoverflow.com/questions/24569399/how-to-post-nested-entities-with-spring-data-rest


### QueryQSL (Web Support)
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

### Liquibase

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

### Hibernate One to Many
link: https://www.baeldung.com/hibernate-one-to-many


......................

### Serverless Postgres DB

link to resource: https://neon.tech/ <br><br>

#### How to connect from Spring Data
Spring Data relies on JDBC and Postgres drivers to connect to Postgres databases. <br>
If you are starting your project with Spring Initializr or connecting from an existing Spring Data project,
ensure that the PostgreSQL database driver dependency is installed. <br>

Connecting from a Spring Data project requires specifying the datasource URL in <br>
<code>application.properties</code> file, as shown in the following example:
<br>

```java 
spring.datasource.url=jdbc:postgresql://[hostname]/[dbname]?user=[user]&password=[password] 
```