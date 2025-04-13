# Capstone Profile API

## Overview
This project is a **Spring Boot REST API** for managing profiles. It connects to an **Azure SQL Database** and provides RESTful endpoints for GET/POST/PUT/DELETE operations.

## Prerequisites
Before running this project, ensure you have the following installed:

- **Maven** ([Download Maven](https://maven.apache.org/download.cgi))
- **Postman** (optional, for API testing - [Download Postman](https://www.postman.com/downloads/))

## Setup Instructions

### Configure Database Connection

#### **Edit `src/main/resources/application.properties`**:
```properties
spring.datasource.url=jdbc:sqlserver://your-server.database.windows.net:1433;database=your-database
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
spring.jpa.hibernate.ddl-auto=update
```
**Replace** `your-server`, `your-database`, `your-username`, and `your-password` with your actual Azure SQL credentials.

### Install Dependencies
Run the following command to install all necessary dependencies for Spring Boot:
```bash
mvn clean install
```

### Run the Spring Boot Application
```bash
mvn spring-boot:run
```
Once the server starts, you should see output confirming that **Tomcat started on port 8080**.

### Test the API in Postman
#### **Check if the API is running:**
- Open **Postman** 
- Send a **GET request** to:
```
http://localhost:8080/api/test
```
Expected Response:
```json
"Hello, Spring Boot is working!"
```