# Employee Management System

A comprehensive Employee Management System built with Spring Boot 3, Spring Security, and JWT authentication.

## Features

- **User Authentication**: JWT-based authentication with login and registration
- **Role-based Access Control**: Three roles - ADMIN, MANAGER, EMPLOYEE
- **Email Verification**: Email verification with verification codes
- **Password Reset**: Forgot password functionality with reset codes
- **Task Management**: Create, assign, and manage tasks
- **User Management**: Admin and Manager can manage users

## Technology Stack

- Java 17+
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- JWT (jjwt)
- MySQL/PostgreSQL
- Lombok

## Project Structure

```
src/main/java/com/employeemanagement/app/
├── config/           # Configuration classes
├── controller/       # REST API controllers
├── dto/              # Data Transfer Objects
├── entity/           # Entity classes
├── exception/        # Custom exceptions
├── repository/       # Repository interfaces
├── security/         # Security related classes
└── service/          # Service classes
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token
- `POST /api/auth/verify-email` - Verify email with code
- `POST /api/auth/resend-verification` - Resend verification code
- `POST /api/auth/forgot-password` - Request password reset
- `POST /api/auth/reset-password` - Reset password with code

### Users
- `GET /api/users` - Get all users (Admin/Manager)
- `GET /api/users/me` - Get current user
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/manager/{managerId}` - Get users by manager
- `POST /api/users` - Create user (Admin only)
- `PUT /api/users/{id}` - Update user (Admin only)
- `DELETE /api/users/{id}` - Delete user (Admin only)

### Tasks
- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task by ID
- `GET /api/tasks/created-by/{creatorId}` - Get tasks by creator
- `GET /api/tasks/assigned-to/{assigneeId}` - Get tasks by assignee
- `POST /api/tasks` - Create task
- `PUT /api/tasks/{id}` - Update task
- `PATCH /api/tasks/{id}/status` - Update task status
- `DELETE /api/tasks/{id}` - Delete task (Admin/Manager)

## Configuration

Configure the application in `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management
spring.datasource.username=root
spring.datasource.password=password

# JWT
jwt.secret=your-256-bit-secret-key-here-must-be-at-least-256-bits
jwt.expiration=86400000

# Email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

## Building and Running

```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

## Default Roles

- **ADMIN**: Full access to all resources
- **MANAGER**: Can manage users and tasks within their team
- **EMPLOYEE**: Can view and manage their own tasks

## Security

- Passwords are encrypted using BCrypt
- JWT tokens are used for authentication
- Tokens expire after 24 hours (configurable)
- Role-based authorization on all endpoints