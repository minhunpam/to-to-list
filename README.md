# Todo List Project

In this project, I am trying to make use of the following technologies:
- a REST API - Spring Boot
- a database: H2 for development and testing and PostgreSQL for production
- a basic web UI - plain HTML/CSS/JS that talk to the API

## Prerequisites & Set-up
- Java version: Java 21
- Build system: Maven
- Dependencies: <added to `pom.xml`>
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Validation
  - H2 Database

## 1. Data Modelling and Wire Persistence
### 1.1. Create the `Todo` JPA `@Entity` so that it can be persisted in the database
    - An entity represents a table in a relational database -> each attribute of the entity represents a column in the table
    - For primary key, use `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` to auto-generate the ID
    - For other attributes, use `@Column` to map them to database columns with specific constraints (e.g., `nullable = false`)

```
todos:
    |-- id (Long, Primary Key, Auto-increment)
    |-- title (String, Not Null, Max Length 100)
    |-- completed (Boolean, Not Null, Default False)
    |-- dueDate (LocalDate, Nullable)
    |-- priority (Enum: LOW, MEDIUM, HIGH, Not Null, Default MEDIUM)
    |-- createdAt (LocalDateTime, Not Null, Default Current Timestamp)
    |-- updatedAt (LocalDateTime, Not Null, Default Current Timestamp)
```

- Besides we have to ensure the lifecycle of an entity is properly managed by using `@PrePersist` for creation and `@PreUpdate` for updates.

### 1.2. Create the `TodoRepository` interface that extends `JpaRepository<Todo, Long>`
- This interface will provide CRUD operations for the `Todo` entity without the need for boilerplate code
- Spring Data JPA will automatically implement this interface at runtime
***Note: because each method in the repository encodes the query, BE CAREFUL with Naming Convention***

### 1.3. Configure H2 for development and testing
- Instead of using the `application.properties` file, we will use `application.yml` file
- Create an **in-memory** H2 database which lives inside app's JVM and vanishes when the app stops (`DB_CLOSE_DELAY=-1`)
    - tell H2 to behave like PostgreSQL (`MODE=PostgreSQL`)
    - enable the H2 console for easy access (`spring.h2.console.enabled=true`) with path `/h2-console`

### 1.4. Prove our chain (Entity -> Repository -> Hibernate -> Database) works
- Create a simple `CommandLineRunner` bean that runs at application startup by seeding the database with a starter record -> prove the persistence setup works

## 2. Build the service layer
- In the service layer, we will implement the business logic and interact with the repository layer to perform CRUD operations on the `Todo` entity.
- The service layer serves as middleman between the controller layer (handling HTTP requests) and the repository layer (interacting with the database).

## 3. Build the REST Controller
- Create a REST Controller that handles HTTP requests and responses for the `Todo` entity.
- The controller will use the service layer to perform CRUD operations and return appropriate HTTP status codes and responses.
- Implement the following endpoints:
    - `POST /api/todos`: Create a new todo item
    - `GET /api/todos`: Retrieve all todo items
    - `GET /api/todos/{id}`: Retrieve a specific todo item by ID
    - `PUT /api/todos/{id}`: Update an existing todo item by ID
    - `DELETE /api/todos/{id}`: Delete a specific todo item by ID

## 4. Create DTO
### 4.1. Create 2 DTOs (Data Transfer Object) classes: `TodoRequest` and `TodoResponse`
  - `TodoRequest` will be used to receive data from the client when creating or updating a todo item
  - `TodoResponse` will be used to send data back to the client when retrieving todo items

