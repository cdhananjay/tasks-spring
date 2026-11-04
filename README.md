# Tasks

REST API for managing task lists and their tasks, built with Spring Boot.

## Stack

- Java 21
- Spring Boot 4.0
- Spring Data JPA + PostgreSQL (H2 for tests)
- springdoc OpenAPI (Swagger UI)
- Maven

## Getting started

Start the database:

```bash
docker compose up -d db
```

Run the application (defaults to port `8080`):

```bash
./mvnw spring-boot:run
```

Run the tests (uses an in-memory H2 database):

```bash
./mvnw test
```

## API

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/task-lists` | List task lists |
| `POST` | `/task-lists` | Create a task list |
| `GET` | `/task-lists/{id}` | Get a task list |
| `PUT` | `/task-lists/{id}` | Update a task list |
| `DELETE` | `/task-lists/{id}` | Delete a task list |
| `GET` | `/task-lists/{taskListId}/tasks` | List tasks in a task list |
| `POST` | `/task-lists/{taskListId}/tasks` | Create a task in a task list |
| `GET` | `/task-lists/{taskListId}/tasks/{taskId}` | Get a task |
| `PUT` | `/task-lists/{taskListId}/tasks/{taskId}` | Update a task |
| `DELETE` | `/task-lists/{taskListId}/tasks/{taskId}` | Delete a task |

Interactive API docs are available at <http://localhost:8080/swagger-ui.html>, with the OpenAPI spec at <http://localhost:8080/v3/api-docs>.
