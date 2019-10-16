# Dummy Rest API Example
Simple REST API service for mentees to practice API testing

## Api documentation
* http://localhost:8080/api/swagger-ui.html

## Build and start service using Maven
```
mvn clean install
mvn spring-boot:run
```

## Build and start service as a daemon using Docker
```
docker build -t dummyrestapi .
docker run -d -p 80:8080 dummyrestapi
```
