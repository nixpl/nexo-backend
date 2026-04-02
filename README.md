# Nexo Backend

"Nexo" system - backend part (Spring Boot 3 + Java + PostgreSQL).

## Requirements
* Java 25
* Docker and Docker Compose (to run the database)
* Maven (the `mvnw` wrapper is available in the project)

## Running the project

1. **Running the database (PostgreSQL)**
   To run a dedicated PostgreSQL database on port `5433` (configured with appropriate credentials for the application), use the provided `docker-compose.yml` file:
   ```bash
   docker-compose up -d
   ```
   *(This command will pull the image and run the database in the background).*

2. **Running the application**
   With the database running, you can start the backend using the built-in Maven wrapper:
   For Linux / macOS:
   ```bash
   ./mvnw spring-boot:run
   ```
   For Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

   *Optional:* You can also simply run the main `NexoBackendApplication` class directly from your IDE (e.g., IntelliJ IDEA).

> Note: Currently, the application is configured in `application.yml` with `ddl-auto: create`, which means that **every time the database and application are started, the tables are recreated from scratch**. This is useful during the testing and development phase.

## API Documentation (Swagger)

The application provides interactive endpoint documentation generated using OpenAPI/Swagger.

Once the application is running, the documentation is available in your browser at:

👉 **[http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)**

*(The `/api` prefix comes from the `server.servlet.context-path` setting in the project configuration).*

You can find the list of all endpoints in JSON format at:
[http://localhost:8080/api/v3/api-docs](http://localhost:8080/api/v3/api-docs)

## Postman Testing

In the `postman_workspaces_for_testing` folder, you will find JSON files that can be imported to Postman for convenient API testing.
