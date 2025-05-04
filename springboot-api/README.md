## ⚙️ Setup and Configuration Guide for Springboot API

### 1. 📦 Dependencies

This project uses **Maven** to manage dependencies. Key technologies included:

- Spring Boot Web & Data JPA
- Microsoft SQL Server JDBC Driver
- Azure Blob Storage SDK
- JUnit 5 + Mockito for testing
- Lombok for reducing boilerplate code

All dependencies are defined in `pom.xml`. To download them locally, run:

```bash
mvn clean install
```

---

### 2. 🔐 application.properties

Before running the project, configure the following values in `src/main/resources/application.properties`:

```properties
# Application Info
spring.application.name=capstone-api

# Azure SQL Database
spring.datasource.url=jdbc:sqlserver://<your-db-server>.database.windows.net:1433;database=<your-db-name>;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# Azure Blob Storage
azure.storage.connection-string=DefaultEndpointsProtocol=https;AccountName=<your-account>;AccountKey=<your-key>;EndpointSuffix=core.windows.net
azure.storage.container-name=profiles

# JPA & Hibernate
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# SQL Initialization
spring.datasource.initialization-mode=always
spring.sql.init.mode=always
```

> 💡 **Do not commit credentials**. Use environment variables or secrets management for deployment.

---

### 3. ▶️ Running the API Locally

To start the Spring Boot app locally:

```bash
mvn spring-boot:run
```

This will launch the API at:

```
http://localhost:8080
```

Make sure your Azure SQL and Blob Storage credentials are valid and accessible.

---

### 4. 🧪 Running Tests

This project uses **JUnit 5** and **Mockito** for unit and integration testing.

To run all tests:

```bash
mvn test
```

Tests are located in `src/test/java/app/controller/` and `src/test/java/app/services/`.

---

Let me know if you want to add Docker setup or deployment instructions too!
