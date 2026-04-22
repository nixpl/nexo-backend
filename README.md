# Nexo Backend

"Nexo" system - backend part (Spring Boot 3 + Java + PostgreSQL).

## Requirements
* Java 25
* Docker and Docker Compose (to run the database)
* Maven (the `mvnw` wrapper is available in the project)

## Environment Variables

To enable the feature of sending email invitations to new users, you need to set up the following environment variables before running the application. These will provide the credentials for the SMTP mail server:

* `MAIL_USERNAME` - The email address from which invitations will be sent (e.g., `your_email@gmail.com`).
* `MAIL_PASSWORD` - The password for the email account (if using a Gmail account, you must generate and use a 16-character **App Password**).

*Optional variables (already configured for Gmail by default):*
* `MAIL_HOST` (default: `smtp.gmail.com`)
* `MAIL_PORT` (default: `587`)

> **Note:** Without setting `MAIL_USERNAME` and `MAIL_PASSWORD`, the application will fail to send invitation emails.

## Database Structure

The system uses a relational database model conceptually based on Jira's structure. Here are the core entities and their relationships (unidirectional mostly for performance):

*   **User**: Core identity.
    *   *Relationships*: Belongs to one `Organization` (Many-to-One).
*   **Organization**: Represents a company or workspace.
    *   *Fields*: `name`, `createdAt`.
*   **Board**: A project/board within an organization.
    *   *Relationships*: Belongs to an `Organization` (Many-to-One). Has assigned `Users` (Many-to-Many). Contains `Stages` (One-to-Many).
*   **Stage**: Represents columns on a board (e.g., *To-Do*, *In Progress*).
    *   *Relationships*: Belongs to a `Board` (Many-to-One). Has a specific `StageType` enum.
*   **Issue**: Represents a task, bug, story, or epic.
    *   *Relationships*: 
        *   Belongs to an `Organization`, `Board`, and `Stage` (Many-to-One).
        *   Assigned to an `assignee` (`User`) and reported by a `reporter` (`User`).
        *   Linked to a parent Epic (`epic_id` -> Many-to-One self-reference).
*   **Comment**: Discussion entries on issues.
    *   *Relationships*: Belongs to an `Issue` (Many-to-One) and written by an `author` (`User`).
*   **Invitation**: Used for inviting new users to an Organization.

## Running the project
 
 To run the entire project (both the PostgreSQL database and the Spring Boot backend) in Docker containers with a single command, use Docker Compose:
 
 ```bash
 docker compose up --build
 ```
 *(This will build the application image and start both the database and backend. Logs will be visible in the console. If you want to run it in the background, add the `-d` flag.)*
 
 Remember to pass the required environment variables if you want email invitations to work, for example:
 ```bash
 MAIL_USERNAME=your_email@gmail.com MAIL_PASSWORD=your_app_password docker compose up --build
 ```
 
 To stop the application:
 * If running in the foreground (without `-d`), simply press `Ctrl + C` in the terminal.
 * If running in the background (with `-d`), use the following command:
 ```bash
 docker compose down
 ```
 
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
