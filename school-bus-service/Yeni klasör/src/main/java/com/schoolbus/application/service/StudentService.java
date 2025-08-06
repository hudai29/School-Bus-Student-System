package com.schoolbus.application.service;

import com.schoolbus.domain.dto.StudentDto;

import java.util.List;

/**
 * Service interface for Student business operations.
 * Defines the contract for student management operations.
 */
public interface StudentService {
    
    /**
     * Creates a new student
     * @param studentDto the student data to create
     * @return the created student DTO
     * @throws IllegalArgumentException if student ID already exists or data is invalid
     */
    StudentDto createStudent(StudentDto studentDto);
    
    /**
     * Retrieves all students ordered by first name
     * @return list of all students
     */
    List<StudentDto> getAllStudents();
    
    /**
     * Retrieves a student by their database ID
     * @param id the database ID of the student
     * @return the student DTO
     * @throws RuntimeException if student not found
     */
    StudentDto getStudentById(Long id);
    
    /**
     * Retrieves a student by their student ID
     * @param studentId the unique student ID
     * @return the student DTO
     * @throws RuntimeException if student not found
     */
    StudentDto getStudentByStudentId(String studentId);
    
    /**
     * Updates an existing student
     * @param id the database ID of the student to update
     * @param studentDto the updated student data
     * @return the updated student DTO
     * @throws RuntimeException if student not found
     * @throws IllegalArgumentException if student ID conflict occurs
     */
    StudentDto updateStudent(Long id, StudentDto studentDto);
    
    /**
     * Deletes a student by their database ID
     * @param id the database ID of the student to delete
     * @throws RuntimeException if student not found
     */
    void deleteStudent(Long id);
    
    /**
     * Retrieves all students in a specific grade
     * @param grade the grade to filter by
     * @return list of students in the specified grade
     */
    List<StudentDto> getStudentsByGrade(String grade);
    
    /**
     * Retrieves all students assigned to a specific bus route
     * @param busRoute the bus route to filter by
     * @return list of students on the specified bus route
     */
    List<StudentDto> getStudentsByBusRoute(String busRoute);
    
    /**
     * Searches for students by name (first or last name)
     * @param name the name to search for (partial matching)
     * @return list of students matching the search criteria
     */
    List<StudentDto> searchStudentsByName(String name);
    
    /**
     * Retrieves students within a specific age range
     * @param minAge minimum age (inclusive)
     * @param maxAge maximum age (inclusive)
     * @return list of students within the age range
     */
    List<StudentDto> getStudentsByAgeRange(Integer minAge, Integer maxAge);
    
    /**
     * Assigns bus route information to a student
     * @param id the database ID of the student
     * @param busRoute the bus route to assign
     * @param pickupTime the pickup time
     * @param dropoffTime the dropoff time
     * @return the updated student DTO
     * @throws RuntimeException if student not found
     */
    StudentDto assignBusRoute(Long id, String busRoute, String pickupTime, String dropoffTime);
    
    /**
     * Gets the count of students in a specific grade
     * @param grade the grade to count
     * @return number of students in the grade
     */
    long getStudentsCountByGrade(String grade);
    
    /**
     * Gets the count of students on a specific bus route
     * @param busRoute the bus route to count
     * @return number of students on the bus route
     */
    long getStudentsCountByBusRoute(String busRoute);
    
    /**
     * Gets all distinct grades in the system
     * @return list of all grades
     */
    List<String> getAllGrades();
    
    /**
     * Gets all distinct bus routes in the system
     * @return list of all bus routes
     */
    List<String> getAllBusRoutes();
    
    /**
     * Gets students without assigned bus routes
     * @return list of students without bus route assignments
     */
    List<StudentDto> getStudentsWithoutBusRoute();
    
    /**
     * Validates student data before creation or update
     * @param studentDto the student data to validate
     * @throws IllegalArgumentException if validation fails
     */
    void validateStudentData(StudentDto studentDto);
}