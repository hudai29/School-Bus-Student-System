# School Bus Service API Documentation

## Overview
This document provides comprehensive information about the School Bus Service REST API endpoints, request/response formats, and usage examples.

## Base URL
```
http://localhost:8080/api
```

## Authentication
Currently, the API does not require authentication. For production deployment, consider implementing OAuth2 or JWT-based authentication.

## API Endpoints

### 1. Student Management

#### Create Student
- **Endpoint**: `POST /students`
- **Description**: Creates a new student record
- **Request Body**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "studentId": "STU001",
  "age": 10,
  "grade": "5th Grade",
  "address": "123 Main Street, City, State 12345",
  "parentContact": "5551234567",
  "busRoute": "Route-A",
  "pickupTime": "07:30",
  "dropoffTime": "15:30"
}
```
- **Response**: `201 Created`
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "studentId": "STU001",
  "age": 10,
  "grade": "5th Grade",
  "address": "123 Main Street, City, State 12345",
  "parentContact": "5551234567",
  "busRoute": "Route-A",
  "pickupTime": "07:30",
  "dropoffTime": "15:30",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### Get All Students
- **Endpoint**: `GET /students`
- **Description**: Retrieves all students ordered by first name
- **Response**: `200 OK`
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "studentId": "STU001",
    "age": 10,
    "grade": "5th Grade",
    "address": "123 Main Street, City, State 12345",
    "parentContact": "5551234567",
    "busRoute": "Route-A",
    "pickupTime": "07:30",
    "dropoffTime": "15:30",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

#### Get Student by ID
- **Endpoint**: `GET /students/{id}`
- **Description**: Retrieves a specific student by database ID
- **Parameters**: 
  - `id` (path): Student database ID
- **Response**: `200 OK` or `404 Not Found`

#### Get Student by Student ID
- **Endpoint**: `GET /students/student-id/{studentId}`
- **Description**: Retrieves a specific student by their student ID
- **Parameters**: 
  - `studentId` (path): Unique student identifier
- **Response**: `200 OK` or `404 Not Found`

#### Update Student
- **Endpoint**: `PUT /students/{id}`
- **Description**: Updates an existing student record
- **Parameters**: 
  - `id` (path): Student database ID
- **Request Body**: Same as Create Student
- **Response**: `200 OK` or `404 Not Found`

#### Delete Student
- **Endpoint**: `DELETE /students/{id}`
- **Description**: Deletes a student record
- **Parameters**: 
  - `id` (path): Student database ID
- **Response**: `204 No Content` or `404 Not Found`

### 2. Search and Filter Operations

#### Get Students by Grade
- **Endpoint**: `GET /students/grade/{grade}`
- **Description**: Retrieves all students in a specific grade
- **Parameters**: 
  - `grade` (path): Grade level (e.g., "5th Grade")
- **Response**: `200 OK`

#### Get Students by Bus Route
- **Endpoint**: `GET /students/bus-route/{busRoute}`
- **Description**: Retrieves all students assigned to a specific bus route
- **Parameters**: 
  - `busRoute` (path): Bus route identifier
- **Response**: `200 OK`

#### Search Students by Name
- **Endpoint**: `GET /students/search?name={name}`
- **Description**: Searches for students by first or last name
- **Parameters**: 
  - `name` (query): Name to search for (partial matching)
- **Response**: `200 OK`

#### Get Students by Age Range
- **Endpoint**: `GET /students/age-range?minAge={minAge}&maxAge={maxAge}`
- **Description**: Retrieves students within a specific age range
- **Parameters**: 
  - `minAge` (query): Minimum age (inclusive)
  - `maxAge` (query): Maximum age (inclusive)
- **Response**: `200 OK`

### 3. Bus Route Management

#### Assign Bus Route
- **Endpoint**: `PUT /students/{id}/assign-bus-route`
- **Description**: Assigns bus route information to a student
- **Parameters**: 
  - `id` (path): Student database ID
  - `busRoute` (query): Bus route identifier
  - `pickupTime` (query): Pickup time (e.g., "07:30")
  - `dropoffTime` (query): Dropoff time (e.g., "15:30")
- **Response**: `200 OK` or `404 Not Found`

### 4. Statistics and Reporting

#### Get Student Count by Grade
- **Endpoint**: `GET /students/count/grade/{grade}`
- **Description**: Returns the number of students in a specific grade
- **Parameters**: 
  - `grade` (path): Grade level
- **Response**: `200 OK`
```json
15
```

#### Get Student Count by Bus Route
- **Endpoint**: `GET /students/count/bus-route/{busRoute}`
- **Description**: Returns the number of students on a specific bus route
- **Parameters**: 
  - `busRoute` (path): Bus route identifier
- **Response**: `200 OK`
```json
25
```

## Data Validation Rules

### Student Creation/Update Validations:
- **firstName**: Required, 2-50 characters
- **lastName**: Required, 2-50 characters
- **studentId**: Required, unique, 5-20 characters
- **age**: Required, between 3 and 18
- **grade**: Required, not empty
- **address**: Required, 10-200 characters
- **parentContact**: Required, valid phone number (10-15 digits)
- **busRoute**: Optional
- **pickupTime**: Optional
- **dropoffTime**: Optional

## Error Responses

### Common Error Format:
```json
{
  "status": 400,
  "message": "Error description",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Validation Error Format:
```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2024-01-15T10:30:00",
  "errors": {
    "firstName": "First name is required",
    "age": "Age must be between 3 and 18"
  }
}
```

### HTTP Status Codes:
- `200 OK`: Request successful
- `201 Created`: Resource created successfully
- `204 No Content`: Resource deleted successfully
- `400 Bad Request`: Invalid request data
- `404 Not Found`: Resource not found
- `409 Conflict`: Resource conflict (e.g., duplicate student ID)
- `500 Internal Server Error`: Server error

## Example Usage with cURL

### Create a Student:
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "studentId": "STU002",
    "age": 11,
    "grade": "6th Grade",
    "address": "456 Oak Avenue, City, State 12345",
    "parentContact": "5552345678"
  }'
```

### Get All Students:
```bash
curl -X GET http://localhost:8080/api/students
```

### Search Students by Name:
```bash
curl -X GET "http://localhost:8080/api/students/search?name=Jane"
```

### Update Student:
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane Updated",
    "lastName": "Smith Updated",
    "studentId": "STU002",
    "age": 12,
    "grade": "7th Grade",
    "address": "789 Updated Street",
    "parentContact": "5559876543"
  }'
```

### Assign Bus Route:
```bash
curl -X PUT "http://localhost:8080/api/students/1/assign-bus-route?busRoute=Route-B&pickupTime=08:00&dropoffTime=16:00"
```

### Delete Student:
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

## Swagger UI Documentation

Interactive API documentation is available at:
```
http://localhost:8080/api/swagger-ui.html
```

The OpenAPI specification can be accessed at:
```
http://localhost:8080/api/api-docs
```

## Rate Limiting and Performance

- No rate limiting is currently implemented
- The API supports standard HTTP caching headers
- For production deployment, consider implementing:
  - Rate limiting per client IP
  - Request/response compression
  - Database connection pooling
  - Caching for frequently accessed data

## Production Considerations

1. **Security**: Implement authentication and authorization
2. **Logging**: Configure appropriate log levels and log aggregation
3. **Monitoring**: Set up health checks and metrics collection
4. **Database**: Use connection pooling and optimize queries
5. **Error Handling**: Implement comprehensive error tracking
6. **Documentation**: Keep API documentation up to date

## Support

For questions, issues, or feature requests, please contact the development team or create an issue in the project repository.