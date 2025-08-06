package com.schoolbus.infrastructure.repository;

import com.schoolbus.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Find student by unique student ID
     * @param studentId the student ID to search for
     * @return Optional containing the student if found
     */
    Optional<Student> findByStudentId(String studentId);
    
    /**
     * Check if a student exists with the given student ID
     * @param studentId the student ID to check
     * @return true if student exists, false otherwise
     */
    boolean existsByStudentId(String studentId);
    
    /**
     * Find all students by grade, ordered by first name
     * @param grade the grade to search for
     * @return List of students in the specified grade
     */
    List<Student> findByGradeOrderByFirstNameAsc(String grade);
    
    /**
     * Find all students by bus route, ordered by pickup time
     * @param busRoute the bus route to search for
     * @return List of students assigned to the specified bus route
     */
    List<Student> findByBusRouteOrderByPickupTimeAsc(String busRoute);
    
    /**
     * Find students by age range
     * @param minAge minimum age (inclusive)
     * @param maxAge maximum age (inclusive)
     * @return List of students within the age range
     */
    List<Student> findByAgeBetweenOrderByAgeAsc(Integer minAge, Integer maxAge);
    
    /**
     * Search students by first name or last name (case-insensitive)
     * @param firstName partial first name to search
     * @param lastName partial last name to search
     * @return List of students matching the search criteria
     */
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "ORDER BY s.firstName ASC")
    List<Student> searchByName(@Param("name") String name);
    
    /**
     * Count students by grade
     * @param grade the grade to count
     * @return number of students in the grade
     */
    long countByGrade(String grade);
    
    /**
     * Count students by bus route
     * @param busRoute the bus route to count
     * @return number of students assigned to the bus route
     */
    long countByBusRoute(String busRoute);
    
    /**
     * Find all students ordered by first name
     * @return List of all students ordered by first name
     */
    List<Student> findAllByOrderByFirstNameAsc();
    
    /**
     * Find students by grade and bus route
     * @param grade the grade to filter by
     * @param busRoute the bus route to filter by
     * @return List of students matching both criteria
     */
    List<Student> findByGradeAndBusRouteOrderByFirstNameAsc(String grade, String busRoute);
    
    /**
     * Find students with no bus route assigned
     * @return List of students without bus route assignment
     */
    List<Student> findByBusRouteIsNullOrderByFirstNameAsc();
    
    /**
     * Find students by parent contact
     * @param parentContact the parent contact number
     * @return List of students with the given parent contact
     */
    List<Student> findByParentContact(String parentContact);
    
    /**
     * Get all distinct grades
     * @return List of distinct grade values
     */
    @Query("SELECT DISTINCT s.grade FROM Student s ORDER BY s.grade")
    List<String> findAllDistinctGrades();
    
    /**
     * Get all distinct bus routes
     * @return List of distinct bus route values
     */
    @Query("SELECT DISTINCT s.busRoute FROM Student s WHERE s.busRoute IS NOT NULL ORDER BY s.busRoute")
    List<String> findAllDistinctBusRoutes();
}