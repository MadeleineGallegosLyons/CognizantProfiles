## 📁 App Layers 

### config
Contains configuration files for external services. Currently includes `AzureBlobStorageConfig.java` to configure the Azure Blob Storage client.

### controller
Includes all REST API controllers that expose endpoints for importing, searching, and deleting profiles.

### domain
Defines the core business entities (`Profile`, `SectionCategory`, `SectionContent`) that are mapped to database tables.

### dto
Contains Data Transfer Objects (DTOs) that structure the request and response payloads for API operations.

### infrastructure
Holds repositories and external integration logic, including JPA interfaces, custom queries, and Azure blob services.

### services
Implements the application’s business logic, such as importing profiles from blobs, deleting profiles, and running keyword searches.
