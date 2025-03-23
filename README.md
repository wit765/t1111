# User API Server

A simple RESTful API server for user management, built with Java and PostgreSQL.

## Prerequisites

- Java 17 or later
- PostgreSQL 12 or later
- Maven (for dependency management)

## Database Setup

1. Create the database:
```sql
CREATE DATABASE employees1;
```

2. Connect to the database and create the table:
```sql
\c employees1

CREATE TABLE employees1 (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL
);
```

3. Insert test data:
```sql
INSERT INTO employees1 (name, age) VALUES 
    ('张三', 25),
    ('李四', 30),
    ('王五', 28),
    ('赵六', 35),
    ('孙七', 22);
```

## Database Configuration

The database connection settings are in `DatabaseConfig.java`:
- Database URL: `jdbc:postgresql://localhost:5432/employees1`
- Username: `postgres`
- Password: `Maleipiye21`

## Setup

1. Download dependencies:
```bash
.\download_dependencies.bat
```

2. Run the application:
```bash
.\run.bat
```

The server will start on port 8080.

## API Endpoints

### Create User
```http
POST http://localhost:8080/users
Content-Type: application/json

{
    "name": "张三",
    "age": 25
}
```

### Get All Users
```http
GET http://localhost:8080/users
```

### Get User by ID
```http
GET http://localhost:8080/users/1
```

### Update User
```http
PUT http://localhost:8080/users/1
Content-Type: application/json

{
    "name": "张三",
    "age": 26
}
```

### Delete User
```http
DELETE http://localhost:8080/users/1
```

## Testing with VSCode REST Client

1. Install the "REST Client" extension in VSCode
2. Open `user-api.http` file
3. Click "Send Request" above each request to test the API

## Project Structure

```
.
├── DatabaseConfig.java    # Database connection configuration
├── User.java             # User model class
├── UserHandler.java      # HTTP request handler
├── UserService.java      # Business logic for user operations
├── UserApiServer.java    # Main server class
├── download_dependencies.bat  # Script to download dependencies
├── run.bat              # Script to compile and run the application
├── user-api.http        # API test file for VSCode REST Client
└── lib/                 # Directory containing dependencies
    ├── gson-2.10.1.jar
    └── postgresql-42.6.0.jar
```

## Dependencies

- Gson 2.10.1 for JSON processing
- PostgreSQL JDBC Driver 42.6.0 for database connectivity

## Error Handling

The API returns appropriate HTTP status codes:
- 200: Success
- 400: Bad Request (invalid input)
- 404: Not Found (user not found)
- 500: Internal Server Error

Error responses include a JSON object with an "error" field containing the error message. # t1111
# t1111
"# t1111" 
"# wit1" 
