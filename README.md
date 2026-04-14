# User Management API

A Spring Boot REST API for managing users. The project demonstrates a clean CRUD flow using controller, service, repository, DTO, validation, exception handling, and MySQL persistence.

## Features

- Create, read, update, and delete users
- Find users by ID, email, username, city, status, country, and status
- Search users by name, email, or username
- Duplicate email and username checks
- Request validation with clear field-level error messages
- Global exception handling
- DTO-based API responses

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Spring Validation
- MySQL
- Lombok
- Maven

## Project Structure

```text
src/main/java/com/zorro/usermanagement
├── controller
├── dto
├── entity
├── exception
├── repository
└── service
```

## Requirements

- Java 17 or newer
- MySQL
- Maven, or use the included Maven wrapper

Check your Java version:

```bash
java -version
```

## Configuration

This project reads database values from environment variables.

| Variable | Example |
| --- | --- |
| `DB_URL` | `jdbc:mysql://localhost:3306/user_management` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `password` |
| `DDL_AUTO` | `update` |
| `SHOW_SQL` | `true` |
| `SERVER_PORT` | `8080` |

Create a MySQL database:

```sql
CREATE DATABASE user_management;
```

Run with environment variables:

```bash
export DB_URL=jdbc:mysql://localhost:3306/user_management
export DB_USERNAME=root
export DB_PASSWORD=your_password
./mvnw spring-boot:run
```

You can also use [application-example.properties](src/main/resources/application-example.properties) as a local template.

## Run Tests

```bash
./mvnw test
```

Tests use an in-memory H2 database, so they do not need your local MySQL credentials.

## Run Application

```bash
./mvnw spring-boot:run
```

Base URL:

```text
http://localhost:8080/api/users
```

## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/users` | Create user |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `GET` | `/api/users/email/{mail}` | Get user by email |
| `GET` | `/api/users/username/{username}` | Get user by username |
| `GET` | `/api/users/city/{city}` | Get users by city |
| `GET` | `/api/users/status/{status}` | Get users by status |
| `GET` | `/api/users/filter?country=India&status=ACTIVE` | Filter by country and status |
| `GET` | `/api/users/search?query=john` | Search users |
| `PUT` | `/api/users/{id}` | Update user |
| `DELETE` | `/api/users/{id}` | Delete user |

## Sample Create Request

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "username": "johndoe",
    "phone": "+91-9876543210",
    "gender": "Male",
    "dateOfBirth": "1995-06-15",
    "address": "123 MG Road",
    "city": "Mumbai",
    "country": "India",
    "status": "ACTIVE"
  }'
```

## Sample Validation Error

```json
{
  "timestamp": "2026-04-14T09:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "fieldErrors": {
    "email": "Email should be valid",
    "username": "Username must be between 3 and 50 characters"
  }
}
```

## More API Details

See [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for detailed request and response examples.
