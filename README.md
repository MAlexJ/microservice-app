## microservices project

### Description

The main idea of the project is to track the status of changes in bills <br>
and notify users about changes in the status of a bill
to which the user has subscribed.

### project settings:

- java 21
- gradle 8.5
- Spring boot 3 (v.3.2.0)

### Project structure

Discovery service - implements the definition of API services in microservices environment

### minimal settings to start project

1. Create .env file in root of the project
2. Add env variables to it

``` java
# app-bill-storage: PostgreSQL
APP_BILL_STORAGE_SERVICE_POSTGRESQL_HOSTNAME
APP_BILL_STORAGE_SERVICE_POSTGRESQL_DBNAME
APP_BILL_STORAGE_SERVICE_POSTGRESQL_USER
APP_BILL_STORAGE_SERVICE_POSTGRESQL_PASSWORD

# app-user-service: PostgreSQL
APP_USER_POSTGRESQL_HOSTNAME
APP_USER_SERVICE_POSTGRESQL_DBNAMEw
APP_USER_SERVICE_POSTGRESQL_USER
APP_USER_SERVICE_POSTGRESQL_PASSWORD

# app-user-subscription-service: Mongodb
APP_USER_SUBSCRIPTION_SERVICE_MONGODB_URI

# app-mail-service: Gmail settings
APP_MAIL_SERVICE_EMAIL_SENDER_FROM
APP_MAIL_SERVICE_EMAIL_USERNAME
APP_MAIL_SERVICE_EMAIL_PASSWORD
APP_MAIL_SERVICE_DEFAULT_RECIPIENT
```

### PostgreSQL

For testing, you can use a free PostgreSQL database - https://www.elephantsql.com/

### Gmail settings

How to set up gmail account is indicated in app-mail-service project's redmi file

### IDE code style

link: https:github.com/google/google-java-format/tree/master


### Grable

Plugins:

1. Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found,
have upgrades, or failed to be resolved

info: https://github.com/ben-manes/gradle-versions-plugin

```
run gradle dependencyUpdates
```