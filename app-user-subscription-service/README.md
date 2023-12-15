# User subscriptionEntity service

## ENV:

```
APP_USER_SUBSCRIPTION_SERVICE_MONGODB_URI
APP_USER_SUBSCRIPTION_SERVICE_MONGODB_DATABASE
```

## Docker

info: https://hub.docker.com/_/mongo

command:

```
    docker pull mongo:latest
```

run mongoDb

```
docker run -d  -p 27017:27017 --name mongo-db-user-subscription \
	-e MONGO_INITDB_ROOT_USERNAME=mongoadmin \
	-e MONGO_INITDB_ROOT_PASSWORD=secret \
	mongo:latest
```

one-line command

```
docker run -d  -p 27017:27017 --name mongo-db-user-subscription -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret mongo:latest	
```

example ENV:

```
APP_USER_SUBSCRIPTION_SERVICE_MONGODB_URI=mongodb://mongoadmin:secret@localhost:27017
```


