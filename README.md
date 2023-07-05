# IoT Infrastructure

### UI

![preview](./images/preview.gif)

### UML

![uml_diagram](./images/uml_diagram.png)

### SQL Database

![sql](./images/sql.png)

### Swagger API
![swag](./images/swag.png)

## Table of Contents
- [Overview](#0-overview)
- [Usage](#1-usage)
- [Project Structure](#2-project-structure)

### 0. Overview

Project was built with Dropwizard which straddles the line between being a library and a framework. Its goal is to provide performant, reliable implementations of everything a production-ready stateless RESTful APIs

1. `Jetty` - dropwizard projects have a `main` method which spins up an HTTP web-server
2. `Jersey` - maps HTTP requests to simple Java objects via RESTful API
3. `Jackson` - king of JSON on the JVM. convert JSON to POJO and vice versa via object mapper
4. `Metrics` - provides ways to measure the behavior of components in production environment
5. `Logback` - the successor to `Log4j`, Java's most widely-used logging framework
6. `Hibernate` - provides user input validation
7. `JDBI` - provides convenient and idiomatic access to relational database with Java

Dropwizard consists mostly of **glue code** to automatically connect and configure these components.

### 1. Usage
To set up the project locally, follow these steps:

1. Clone the repository to your local machine.
2. Make sure you have JDK installed.
3. Build the project using `./gradlew build`
4. Run the project using `./gradlew run` or via IDE configurations with `server config.yml` program arguments

Once the application is running, you can access the RESTful endpoints by navigating to `http://localhost:8080` in your web browser or using tools like cURL or Postman.

### 2. Project Structure
The project structure follows a standard Gradle directory layout:

```
.
├── build.gradle     
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── settings.gradle    
├── src
│   ├── main
│   │   ├── java      
│   │   ├── resources  
│   │   
│   └── test
│       ├── java        
│       └── resources
└── README.md  
└── config.yml       
```