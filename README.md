
# User API



This is spring boot project 


## Overview

API documentaion can be found at [user-api-specification.yaml](https://github.com/mayankisrolling/user-api-repository/blob/master/src/main/resources/user-api-specification.yaml)



All the APIs structure and DTO classes are generated using [OpenAPI Generator](https://openapi-generator.tech) maven plugin.

API specifcations are written  using the [OpenAPI-Spec](https://openapis.org).

For compiling the project and creating the jar:
```mvn clean install```

For running the application :
```mvn spring-boot:run```

The application uses in memomry H2 database:
Database scripts are present in : [data.sql](https://github.com/mayankisrolling/user-api-repository/blob/master/src/main/resources/data.sql)

Once project is running database can also be accessed by browser on : [local console ](http://localhost:8080/h2-console). Details of login into database can be found in application.properties


## Dependencies used:
##### spring-boot-starter-actuator:2.5.6
##### spring-boot-starter:jar:2.5.6
##### spring-boot-starter-logging:2.5.6
##### spring-boot-starter-web:2.5.6
##### spring-boot-starter-json:2.5.6
##### com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.12.5
##### org.mapstruct:mapstruct:1.4.2.Final
##### spring-boot-starter-data-jpa:2.5.6
##### spring-boot-starter-jdbc:2.5.6
##### spring-boot-devtools:2.5.6
##### com.google.code.gson:gson:2.8.9
##### com.fasterxml.jackson.core:jackson-databind:2.13.0
##### com.fasterxml.jackson.core:jackson-core:2.13.0
##### com.fasterxml.jackson.core:jackson-annotations:2.13.0
##### Springfox , Swagger , Lombok

### Plugins:

OpenAPI generator , maven-compiler-plugin,spring-boot-maven-plugin

