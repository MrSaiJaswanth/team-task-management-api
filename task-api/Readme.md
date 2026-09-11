# Team Task Management REST API

A simple REST API built with Java and Spring Boot for a small software development team to create and track development tasks through a clear workflow.

The API supports creating tasks, updating task details, starting work, completing tasks, and deleting tasks.

## Scenario

A small software development team uses this API to manage development tasks.

A typical task follows this workflow:

```text
PENDING → IN_PROGRESS → COMPLETED
```

For example:

* A developer creates a task → `pending`
* The developer starts working → `in_progress`
* The developer finishes the task → `completed`

The project uses in-memory storage to keep the application simple and easy to run.

## Tech Stack

* Java 17+
* Spring Boot
* Spring Web
* Spring Validation
* Spring Security
* Maven
* JUnit 5
* Mockito
* In-memory storage using `ConcurrentHashMap`

## Features

* Create a task
* View all tasks
* View a task by ID
* Update task details
* Start a task
* Complete a task
* Delete a task
* Input validation
* Centralized error handling
* HTTP Basic authentication
* Unit tests for service-layer logic

## Getting Started

### Prerequisites

Make sure you have:

* Java 17 or higher
* Maven
* Git
* Eclipse or another Java IDE


### Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or run `TaskApiApplication.java` directly from Eclipse as a Spring Boot application.

The API will be available at:

```text
http://localhost:8080
```

## Authentication

The API uses HTTP Basic authentication.

Demo credentials:

```text
Username: admin
Password: admin123
```

These credentials are stored in memory and are intended only for this case study.

## API Endpoints

| Method | Endpoint               | Description              |
| ------ | ---------------------- | ------------------------ |
| GET    | `/tasks`               | Get all tasks            |
| GET    | `/tasks/{id}`          | Get a task by ID         |
| POST   | `/tasks`               | Create a new task        |
| PUT    | `/tasks/{id}`          | Update task details      |
| PATCH  | `/tasks/{id}/start`    | Start a task             |
| PATCH  | `/tasks/{id}/complete` | Mark a task as completed |
| DELETE | `/tasks/{id}`          | Delete a task            |

All endpoints require authentication.

## Task Status

Tasks move through the following workflow:

```text
pending
   ↓
in_progress
   ↓
completed
```

New tasks are automatically created with the status `pending`.

## Example

### Create a Task

**POST** `/tasks`

```json
{
  "title": "Implement user login",
  "description": "Add username and password authentication",
  "due_date": "2026-09-20"
}
```

A successful request returns `201 Created`.

Example response:

```json
{
  "id": 1,
  "title": "Implement user login",
  "description": "Add username and password authentication",
  "due_date": "2026-09-20",
  "status": "pending",
  "created_at": "2026-09-11T21:00:00",
  "updated_at": "2026-09-11T21:00:00"
}
```

### Start the Task

**PATCH** `/tasks/1/start`

The task status changes from:

```text
pending → in_progress
```

### Complete the Task

**PATCH** `/tasks/1/complete`

The task status changes from:

```text
in_progress → completed
```

## Validation

The API validates incoming task data.

Examples:

* Title is required
* Title cannot exceed 200 characters
* Description cannot exceed 2000 characters
* Due date cannot be in the past

Invalid requests return `400 Bad Request`.

## Error Handling

The API returns appropriate HTTP status codes for common situations:

| Status             | Meaning                            |
| ------------------ | ---------------------------------- |
| `200 OK`           | Request completed successfully     |
| `201 Created`      | Task created successfully          |
| `204 No Content`   | Task deleted successfully          |
| `400 Bad Request`  | Invalid input                      |
| `401 Unauthorized` | Authentication required or invalid |
| `404 Not Found`    | Task does not exist                |

## Testing

The project uses JUnit 5 and Mockito for unit testing.

Current tests cover:

* Creating a task
* Starting a task
* Completing a task
* Handling a task that does not exist

Run the tests with:

```bash
mvn test
```

## Project Structure

```text
src/
├── main/
│   ├── java/com/example/taskapi/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── exception/
│   │   └── config/
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/com/example/taskapi/
        └── service/
```

### Main Layers

* **Controller** — Handles HTTP requests and responses
* **Service** — Contains task-related business logic
* **Repository** — Manages in-memory task storage
* **Model** — Defines task data and status
* **DTO** — Handles incoming request data and validation
* **Exception** — Handles API errors
* **Config** — Configures authentication and security

## Design Decisions

For a detailed explanation of the architecture, validation, error handling, authentication, storage approach, and assumptions, see:

`DESIGN.md`

## Limitations

This project intentionally keeps the implementation simple for a case study.

* Task data is stored in memory and is lost when the application restarts.
* Authentication uses a single in-memory user.
* No database is required.
* No user registration or role management is included.

These choices keep the project focused on REST API design and core Spring Boot concepts.

## Author

Sai Jaswanth
