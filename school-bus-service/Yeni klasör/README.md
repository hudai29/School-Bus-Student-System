# School Bus Service - CRUD Application

A comprehensive CRUD (Create, Read, Update, Delete) application for managing school bus students built with Spring Boot, PostgreSQL, and Swagger documentation.

## 🏗️ Architecture

This application follows **Clean Architecture** principles with a layered structure:

```
src/main/java/com/schoolbus/
├── domain/                    # Domain Layer (Entities, DTOs)
│   ├── entity/               # JPA Entities
│   └── dto/                  # Data Transfer Objects
├── application/              # Application Layer (Business Logic)
│   └── service/             # Service interfaces and implementations
├── infrastructure/           # Infrastructure Layer (Data Access)
│   └── repository/          # Repository interfaces
└── interfaces/              # Interface Layer (Controllers, Exception Handlers)
    ├── controller/          # REST Controllers
    └── exception/           # Global Exception Handler
```

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger/OpenAPI 3**
- **Maven**

## 📋 Prerequisites

Before running this application, ensure you have:

1. **Java 17** or higher installed
2. **PostgreSQL** database server running
3. **Maven** installed

## 🚀 Setup Instructions

### 1. Database Setup

1. **Install PostgreSQL** (if not already installed)
2. **Create the database:**
   ```sql
   CREATE DATABASE school_bus_db;
   ```
3. **Update database credentials** in `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/school_bus_db
       username: your_username
       password: your_password
   ```

### 2. Application Setup

1. **Clone or download the project**
2. **Navigate to the project directory:**
   ```bash
   cd school-bus-service
   ```
3. **Build the project:**
   ```bash
   mvn clean install
   ```
4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Swagger UI
Access the interactive API documentation at:
- **Swagger UI**: `http://localhost:8080/api/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api/api-docs`

### Available Endpoints

#### Student Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/students` | Create a new student |
| `GET` | `/api/students` | Get all students |
| `GET` | `/api/students/{id}` | Get student by ID |
| `GET` | `/api/students/student-id/{studentId}` | Get student by student ID |
| `PUT` | `/api/students/{id}` | Update student |
| `DELETE` | `/api/students/{id}` | Delete student |

#### Search and Filter

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/students/grade/{grade}` | Get students by grade |
| `GET` | `/api/students/bus-route/{busRoute}` | Get students by bus route |
| `GET` | `/api/students/search?name={name}` | Search students by name |
| `GET` | `/api/students/age-range?minAge={min}&maxAge={max}` | Get students by age range |

#### Bus Route Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `PUT` | `/api/students/{id}/assign-bus-route` | Assign bus route to student |

#### Statistics

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/students/count/grade/{grade}` | Get student count by grade |
| `GET` | `/api/students/count/bus-route/{busRoute}` | Get student count by bus route |

## 📝 Example API Usage

### Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "studentId": "STU001",
    "age": 10,
    "grade": "5th Grade",
    "address": "123 Main Street, City, State 12345",
    "parentContact": "5551234567"
  }'
```

### Get All Students
```bash
curl -X GET http://localhost:8080/api/students
```

### Update a Student
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "studentId": "STU001",
    "age": 11,
    "grade": "6th Grade",
    "address": "123 Main Street, City, State 12345",
    "parentContact": "5551234567"
  }'
```

### Assign Bus Route
```bash
curl -X PUT "http://localhost:8080/api/students/1/assign-bus-route?busRoute=Route-A&pickupTime=07:30&dropoffTime=15:30"
```

## 🏛️ Database Schema

The application automatically creates the following table structure:

```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    student_id VARCHAR(20) UNIQUE NOT NULL,
    age INTEGER NOT NULL,
    grade VARCHAR(50) NOT NULL,
    address VARCHAR(200) NOT NULL,
    parent_contact VARCHAR(15) NOT NULL,
    bus_route VARCHAR(50),
    pickup_time VARCHAR(10),
    dropoff_time VARCHAR(10),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

## 🔧 Configuration

### Application Properties

Key configuration options in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/school_bus_db
    username: postgres
    password: password
  
  jpa:
    hibernate:
      ddl-auto: update  # Automatically create/update tables
    show-sql: true      # Show SQL queries in logs

server:
  port: 8080
  servlet:
    context-path: /api

springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

## 🧪 Testing

Run the tests using Maven:

```bash
mvn test
```

## 📦 Project Structure

```
school-bus-service/
├── src/
│   ├── main/
│   │   ├── java/com/schoolbus/
│   │   │   ├── domain/
│   │   │   │   ├── entity/
│   │   │   │   │   └── Student.java
│   │   │   │   └── dto/
│   │   │   │       └── StudentDto.java
│   │   │   ├── application/
│   │   │   │   └── service/
│   │   │   │       ├── StudentService.java
│   │   │   │       └── impl/
│   │   │   │           └── StudentServiceImpl.java
│   │   │   ├── infrastructure/
│   │   │   │   └── repository/
│   │   │   │       └── StudentRepository.java
│   │   │   ├── interfaces/
│   │   │   │   ├── controller/
│   │   │   │   │   └── StudentController.java
│   │   │   │   └── exception/
│   │   │   │       └── GlobalExceptionHandler.java
│   │   │   └── SchoolBusServiceApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── pom.xml
└── README.md
```

## 🎯 Features

- ✅ **Complete CRUD Operations** for students
- ✅ **Data Validation** with comprehensive error handling
- ✅ **Search and Filter** capabilities
- ✅ **Bus Route Management** for students
- ✅ **Statistics and Reporting** features
- ✅ **Swagger API Documentation** with interactive UI
- ✅ **Clean Architecture** implementation
- ✅ **PostgreSQL Integration** with JPA/Hibernate
- ✅ **Global Exception Handling**
- ✅ **RESTful API Design**

## 🔍 Key Features Explained

### 1. Clean Architecture
- **Domain Layer**: Contains entities and business rules
- **Application Layer**: Contains business logic and use cases
- **Infrastructure Layer**: Contains data access and external services
- **Interface Layer**: Contains controllers and external interfaces

### 2. JPA/Hibernate Integration
- Automatic table creation and updates
- Optimistic locking with version control
- Audit fields (created_at, updated_at)
- Comprehensive validation annotations

### 3. Swagger Documentation
- Interactive API documentation
- Request/response examples
- Parameter validation documentation
- Error response documentation

### 4. Data Validation
- Bean validation annotations
- Custom validation rules
- Comprehensive error messages
- Global exception handling

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/school-bus-service-1.0.0.jar
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 📞 Support

For questions or issues, please create an issue in the repository or contact the development team. 