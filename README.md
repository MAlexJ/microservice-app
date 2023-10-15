## microservices project

### Description

The main idea of the project is to track the status of changes in bills <br>
and notify users about changes in the status of a bill 
to which the user has subscribed. 



### project settings:
- java 17 +
- gradle 8.3 + 
- Spring boot 3

### minimal settings to start project
1. Create .env file in root of the project 
2. Add env variables to it
``` java
# PostgreSQL
APP_STORAGE_SERVICE_POSTGRESQL_HOSTNAME
APP_STORAGE_SERVICE_POSTGRESQL_DBNAME
APP_STORAGE_SERVICE_POSTGRESQL_USER
APP_STORAGE_SERVICE_POSTGRESQL_PASSWORD
# Gmail settings
APP_MAIL_SERVICE_EMAIL_SENDER_FROM
APP_MAIL_SERVICE_EMAIL_USERNAME
APP_MAIL_SERVICE_EMAIL_PASSWORD
APP_MAIL_SERVICE_DEFAULT_RECIPIENT
```

### PostgreSQL
For testing you can use a free PostgreSQL database - https://www.elephantsql.com/

### Gmail settings

How to set up gmail account is indicated in app-mail-service project's redmi file