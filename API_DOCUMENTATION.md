# User Management API Documentation

**Base URL:** `http://localhost:8080/api/users`  
**Content-Type:** `application/json`  
**Spring Boot Version:** `4.0.5` | **Java Version:** `17`

---

## Table of Contents
1. [User Object](#1-user-object)
2. [Create User](#2-create-user)
3. [Get All Users](#3-get-all-users)
4. [Get User by ID](#4-get-user-by-id)
5. [Update User](#5-update-user)
6. [Delete User](#6-delete-user)
7. [Get User by Email](#7-get-user-by-email)
8. [Get User by Username](#8-get-user-by-username)
9. [Get Users by City](#9-get-users-by-city)
10. [Get Users by Status](#10-get-users-by-status)
11. [Filter by Country & Status](#11-filter-by-country--status)
12. [Search Users](#12-search-users)
13. [Error Responses](#13-error-responses)

---

## 1. User Object

### User Fields

| Field         | Type        | Required | Description                              |
|---------------|-------------|----------|------------------------------------------|
| `id`          | `UUID`      | Auto     | Auto-generated primary key               |
| `firstName`   | `String`    | Yes      | User's first name                        |
| `lastName`    | `String`    | Yes      | User's last name                         |
| `email`       | `String`    | Yes      | Unique email address                     |
| `username`    | `String`    | Yes      | Unique username                          |
| `phone`       | `String`    | No       | Phone number                             |
| `gender`      | `String`    | No       | Gender (Male, Female, Other, etc.)       |
| `dateOfBirth` | `LocalDate` | No       | Format: YYYY-MM-DD                       |
| `address`     | `String`    | No       | Street address                           |
| `city`        | `String`    | No       | City of residence                        |
| `country`     | `String`    | No       | Country of residence                     |
| `status`      | `Enum`      | No       | ACTIVE / INACTIVE / SUSPENDED (default: ACTIVE) |
| `createdAt`   | `DateTime`  | Auto     | Auto-set on creation (read-only)         |
| `updatedAt`   | `DateTime`  | Auto     | Auto-updated on every save (read-only)   |

### UserStatus Enum Values
| Value       | Description              |
|-------------|--------------------------|
| `ACTIVE`    | User is active (default) |
| `INACTIVE`  | User is deactivated      |
| `SUSPENDED` | User is suspended/banned |

---

## 2. Create User

**`POST /api/users`**

### Request Body

```json
{
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
}
```

### Response — `201 Created`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
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
  "status": "ACTIVE",
  "createdAt": "2026-04-03T21:30:00",
  "updatedAt": "2026-04-03T21:30:00"
}
```

### Error Responses
| Status | Reason |
|--------|--------|
| `409 Conflict` | Email or username already exists |
| `500 Internal Server Error` | Unexpected server error |

### cURL
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
    "country": "India"
  }'
```

---

## 3. Get All Users

**`GET /api/users`**

### Response — `200 OK`

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
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
    "status": "ACTIVE",
    "createdAt": "2026-04-03T21:30:00",
    "updatedAt": "2026-04-03T21:30:00"
  }
]
```

### cURL
```bash
curl -X GET http://localhost:8080/api/users
```

---

## 4. Get User by ID

**`GET /api/users/{id}`**

### Path Variables
| Parameter | Type   | Description       |
|-----------|--------|-------------------|
| `id`      | `UUID` | User's unique ID |

### Response — `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "username": "johndoe",
  "status": "ACTIVE",
  "createdAt": "2026-04-03T21:30:00",
  "updatedAt": "2026-04-03T21:30:00"
}
```

### Error Responses
| Status | Reason |
|--------|--------|
| `404 Not Found` | No user exists with the given ID |

### cURL
```bash
curl -X GET http://localhost:8080/api/users/550e8400-e29b-41d4-a716-446655440000
```

---

## 5. Update User

**`PUT /api/users/{id}`**

### Path Variables
| Parameter | Type   | Description           |
|-----------|--------|-----------------------|
| `id`      | `UUID` | ID of user to update  |

### Request Body

```json
{
  "firstName": "John",
  "lastName": "Updated",
  "email": "john.updated@example.com",
  "username": "johnupdated",
  "phone": "+91-9000000000",
  "gender": "Male",
  "dateOfBirth": "1995-06-15",
  "address": "789 New Lane",
  "city": "Pune",
  "country": "India",
  "status": "INACTIVE"
}
```

### Response — `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "firstName": "John",
  "lastName": "Updated",
  "email": "john.updated@example.com",
  "username": "johnupdated",
  "phone": "+91-9000000000",
  "status": "INACTIVE",
  "createdAt": "2026-04-03T21:30:00",
  "updatedAt": "2026-04-03T22:00:00"
}
```

### Error Responses
| Status | Reason |
|--------|--------|
| `404 Not Found` | No user exists with the given ID |
| `409 Conflict` | New email or username belongs to another user |

### cURL
```bash
curl -X PUT http://localhost:8080/api/users/550e8400-e29b-41d4-a716-446655440000 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Updated",
    "email": "john.updated@example.com",
    "username": "johnupdated",
    "city": "Pune",
    "country": "India",
    "status": "INACTIVE"
  }'
```

---

## 6. Delete User

**`DELETE /api/users/{id}`**

### Path Variables
| Parameter | Type   | Description           |
|-----------|--------|-----------------------|
| `id`      | `UUID` | ID of user to delete  |

### Response — `204 No Content`

> No response body returned on successful deletion.

### Error Responses
| Status | Reason |
|--------|--------|
| `404 Not Found` | No user exists with the given ID |

### cURL
```bash
curl -X DELETE http://localhost:8080/api/users/550e8400-e29b-41d4-a716-446655440000
```

---

## 7. Get User by Email

**`GET /api/users/email/{email}`**

### Path Variables
| Parameter | Type     | Description          |
|-----------|----------|----------------------|
| `email`   | `String` | User's email address |

### Response — `200 OK`
Returns the matching user object (same as Get by ID response).

### Error Responses
| Status | Reason |
|--------|--------|
| `404 Not Found` | No user found with that email |

### cURL
```bash
curl -X GET http://localhost:8080/api/users/email/john.doe@example.com
```

---

## 8. Get User by Username

**`GET /api/users/username/{username}`**

### Path Variables
| Parameter  | Type     | Description      |
|------------|----------|------------------|
| `username` | `String` | User's username  |

### Response — `200 OK`
Returns the matching user object (same as Get by ID response).

### Error Responses
| Status | Reason |
|--------|--------|
| `404 Not Found` | No user found with that username |

### cURL
```bash
curl -X GET http://localhost:8080/api/users/username/johndoe
```

---

## 9. Get Users by City

**`GET /api/users/city/{city}`**

### Path Variables
| Parameter | Type     | Description  |
|-----------|----------|--------------|
| `city`    | `String` | City name    |

### Response — `200 OK`

```json
[
  { "id": "550e8400-e29b-41d4-a716-446655440000", "firstName": "John", "city": "Mumbai", "status": "ACTIVE" },
  { "id": "660e8400-e29b-41d4-a716-446655440000", "firstName": "Ravi", "city": "Mumbai", "status": "INACTIVE" }
]
```

> Returns `[]` if no users found in that city.

### cURL
```bash
curl -X GET http://localhost:8080/api/users/city/Mumbai
```

---

## 10. Get Users by Status

**`GET /api/users/status/{status}`**

### Path Variables
| Parameter | Type   | Description                               |
|-----------|--------|-------------------------------------------|
| `status`  | `Enum` | One of: `ACTIVE`, `INACTIVE`, `SUSPENDED` |

### Response — `200 OK`

```json
[
  { "id": "550e8400-e29b-41d4-a716-446655440000", "firstName": "John", "status": "ACTIVE" },
  { "id": "660e8400-e29b-41d4-a716-446655440000", "firstName": "Jane", "status": "ACTIVE" }
]
```

### cURL
```bash
curl -X GET http://localhost:8080/api/users/status/ACTIVE
```

---

## 11. Filter by Country & Status

Filters users by both country and status using a JPQL query.

**`GET /api/users/filter`**

### Query Parameters
| Parameter | Type     | Required | Description                               |
|-----------|----------|----------|-------------------------------------------|
| `country` | `String` | Yes      | Country name (e.g. `India`)               |
| `status`  | `Enum`   | Yes      | One of: `ACTIVE`, `INACTIVE`, `SUSPENDED` |

### Response — `200 OK`

```json
[
  { "id": "550e8400-e29b-41d4-a716-446655440000", "firstName": "John", "country": "India", "status": "ACTIVE" },
  { "id": "660e8400-e29b-41d4-a716-446655440000", "firstName": "Jane", "country": "India", "status": "ACTIVE" }
]
```

### cURL
```bash
curl -X GET "http://localhost:8080/api/users/filter?country=India&status=ACTIVE"
```

---

## 12. Search Users

Full-text search across `firstName`, `lastName`, `email`, and `username` using native SQL LIKE (case-insensitive).

**`GET /api/users/search`**

### Query Parameters
| Parameter | Type     | Required | Description                        |
|-----------|----------|----------|------------------------------------|
| `query`   | `String` | Yes      | Search keyword (case-insensitive)  |

### Response — `200 OK`

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "username": "johndoe"
  }
]
```

> Matches any user where the keyword appears in first name, last name, email, or username.

### cURL
```bash
curl -X GET "http://localhost:8080/api/users/search?query=john"
```

---

## 13. Error Responses

All errors return a consistent JSON envelope:

```json
{
  "timestamp": "2026-04-03T21:45:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 99"
}
```

### HTTP Status Code Reference

| Code  | Meaning | When It Occurs |
|-------|---------|----------------|
| `200` | OK | GET / PUT success |
| `201` | Created | POST create user success |
| `204` | No Content | DELETE success |
| `404` | Not Found | User ID / email / username doesn't exist |
| `409` | Conflict | Email or username already in use |
| `500` | Internal Server Error | Unexpected server failure |

---

## Quick Reference

| Method   | Endpoint                                   | Description              |
|----------|--------------------------------------------|--------------------------|
| `POST`   | `/api/users`                               | Create a new user        |
| `GET`    | `/api/users`                               | Get all users            |
| `GET`    | `/api/users/{id}`                          | Get user by ID           |
| `PUT`    | `/api/users/{id}`                          | Update user by ID        |
| `DELETE` | `/api/users/{id}`                          | Delete user by ID        |
| `GET`    | `/api/users/email/{email}`                 | Get user by email        |
| `GET`    | `/api/users/username/{username}`           | Get user by username     |
| `GET`    | `/api/users/city/{city}`                   | Get users by city        |
| `GET`    | `/api/users/status/{status}`               | Get users by status      |
| `GET`    | `/api/users/filter?country=X&status=Y`     | Filter by country+status |
| `GET`    | `/api/users/search?query=X`                | Full-text search         |
