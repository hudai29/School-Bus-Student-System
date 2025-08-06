package com.schoolbus;

import com.schoolbus.application.service.StudentService;
import com.schoolbus.domain.dto.StudentDto;
import com.schoolbus.domain.entity.Student;
import com.schoolbus.infrastructure.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Comprehensive test suite for StudentService.
 * Tests all CRUD operations and business logic validation.
 */
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Student Service Tests")
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;
    
    private StudentDto validStudentDto;
    private Student mockStudent;

    @BeforeEach
    void setUp() {
        validStudentDto = new StudentDto(
                "John", "Doe", "STU001", 10,
                "5th Grade", "123 Main Street, City, State 12345", "5551234567"
        );
        
        mockStudent = createMockStudent();
    }
    
    @Nested
    @DisplayName("Create Student Tests")
    class CreateStudentTests {
        
        @Test
        @DisplayName("Should create student successfully with valid data")
        public void testCreateStudent() {
            // Given
            when(studentRepository.existsByStudentId("STU001")).thenReturn(false);
            when(studentRepository.save(any(Student.class))).thenReturn(mockStudent);

            // When
            StudentDto result = studentService.createStudent(validStudentDto);

            // Then
            assertNotNull(result);
            assertEquals("John", result.getFirstName());
            assertEquals("Doe", result.getLastName());
            assertEquals("STU001", result.getStudentId());
            assertEquals(10, result.getAge());
            assertEquals("5th Grade", result.getGrade());
            
            verify(studentRepository).existsByStudentId("STU001");
            verify(studentRepository).save(any(Student.class));
        }

        @Test
        @DisplayName("Should throw exception when student ID already exists")
        public void testCreateStudentWithDuplicateId() {
            // Given
            when(studentRepository.existsByStudentId("STU001")).thenReturn(true);

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                studentService.createStudent(validStudentDto);
            });
            
            assertTrue(exception.getMessage().contains("already exists"));
            verify(studentRepository).existsByStudentId("STU001");
            verify(studentRepository, never()).save(any(Student.class));
        }
        
        @Test
        @DisplayName("Should throw exception for null student data")
        public void testCreateStudentWithNullData() {
            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                studentService.createStudent(null);
            });
            
            assertEquals("Student data cannot be null", exception.getMessage());
        }
    }
    
    @Nested
    @DisplayName("Read Student Tests")
    class ReadStudentTests {
        
        @Test
        @DisplayName("Should get all students successfully")
        public void testGetAllStudents() {
            // Given
            List<Student> mockStudents = Arrays.asList(mockStudent, createMockStudent("Jane", "Smith", "STU002"));
            when(studentRepository.findAllByOrderByFirstNameAsc()).thenReturn(mockStudents);

            // When
            List<StudentDto> result = studentService.getAllStudents();

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());
            verify(studentRepository).findAllByOrderByFirstNameAsc();
        }
        
        @Test
        @DisplayName("Should get student by ID successfully")
        public void testGetStudentById() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

            // When
            StudentDto result = studentService.getStudentById(1L);

            // Then
            assertNotNull(result);
            assertEquals("John", result.getFirstName());
            assertEquals(1L, result.getId());
            verify(studentRepository).findById(1L);
        }
        
        @Test
        @DisplayName("Should throw exception when student not found by ID")
        public void testGetStudentByIdNotFound() {
            // Given
            when(studentRepository.findById(999L)).thenReturn(Optional.empty());

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                studentService.getStudentById(999L);
            });
            
            assertTrue(exception.getMessage().contains("not found"));
            verify(studentRepository).findById(999L);
        }
        
        @Test
        @DisplayName("Should get student by student ID successfully")
        public void testGetStudentByStudentId() {
            // Given
            when(studentRepository.findByStudentId("STU001")).thenReturn(Optional.of(mockStudent));

            // When
            StudentDto result = studentService.getStudentByStudentId("STU001");

            // Then
            assertNotNull(result);
            assertEquals("STU001", result.getStudentId());
            verify(studentRepository).findByStudentId("STU001");
        }
        
        @Test
        @DisplayName("Should get students by grade")
        public void testGetStudentsByGrade() {
            // Given
            List<Student> mockStudents = Arrays.asList(mockStudent);
            when(studentRepository.findByGradeOrderByFirstNameAsc("5th Grade")).thenReturn(mockStudents);

            // When
            List<StudentDto> result = studentService.getStudentsByGrade("5th Grade");

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("5th Grade", result.get(0).getGrade());
            verify(studentRepository).findByGradeOrderByFirstNameAsc("5th Grade");
        }
    }
    
    @Nested
    @DisplayName("Update Student Tests")
    class UpdateStudentTests {
        
        @Test
        @DisplayName("Should update student successfully")
        public void testUpdateStudent() {
            // Given
            StudentDto updateDto = new StudentDto(
                    "John Updated", "Doe Updated", "STU001", 11,
                    "6th Grade", "456 Updated Street", "5559876543"
            );
            
            when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
            when(studentRepository.save(any(Student.class))).thenReturn(mockStudent);

            // When
            StudentDto result = studentService.updateStudent(1L, updateDto);

            // Then
            assertNotNull(result);
            verify(studentRepository).findById(1L);
            verify(studentRepository).save(any(Student.class));
        }
        
        @Test
        @DisplayName("Should throw exception when updating non-existent student")
        public void testUpdateNonExistentStudent() {
            // Given
            when(studentRepository.findById(999L)).thenReturn(Optional.empty());

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                studentService.updateStudent(999L, validStudentDto);
            });
            
            assertTrue(exception.getMessage().contains("not found"));
            verify(studentRepository).findById(999L);
            verify(studentRepository, never()).save(any(Student.class));
        }
    }
    
    @Nested
    @DisplayName("Delete Student Tests")
    class DeleteStudentTests {
        
        @Test
        @DisplayName("Should delete student successfully")
        public void testDeleteStudent() {
            // Given
            when(studentRepository.existsById(1L)).thenReturn(true);

            // When
            studentService.deleteStudent(1L);

            // Then
            verify(studentRepository).existsById(1L);
            verify(studentRepository).deleteById(1L);
        }
        
        @Test
        @DisplayName("Should throw exception when deleting non-existent student")
        public void testDeleteNonExistentStudent() {
            // Given
            when(studentRepository.existsById(999L)).thenReturn(false);

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                studentService.deleteStudent(999L);
            });
            
            assertTrue(exception.getMessage().contains("not found"));
            verify(studentRepository).existsById(999L);
            verify(studentRepository, never()).deleteById(anyLong());
        }
    }
    
    @Nested
    @DisplayName("Search and Filter Tests")
    class SearchAndFilterTests {
        
        @Test
        @DisplayName("Should search students by name")
        public void testSearchStudentsByName() {
            // Given
            List<Student> mockStudents = Arrays.asList(mockStudent);
            when(studentRepository.searchByName("John")).thenReturn(mockStudents);

            // When
            List<StudentDto> result = studentService.searchStudentsByName("John");

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(studentRepository).searchByName("John");
        }
        
        @Test
        @DisplayName("Should get students by bus route")
        public void testGetStudentsByBusRoute() {
            // Given
            mockStudent.setBusRoute("Route-A");
            List<Student> mockStudents = Arrays.asList(mockStudent);
            when(studentRepository.findByBusRouteOrderByPickupTimeAsc("Route-A")).thenReturn(mockStudents);

            // When
            List<StudentDto> result = studentService.getStudentsByBusRoute("Route-A");

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(studentRepository).findByBusRouteOrderByPickupTimeAsc("Route-A");
        }
        
        @Test
        @DisplayName("Should get students by age range")
        public void testGetStudentsByAgeRange() {
            // Given
            List<Student> mockStudents = Arrays.asList(mockStudent);
            when(studentRepository.findByAgeBetweenOrderByAgeAsc(8, 12)).thenReturn(mockStudents);

            // When
            List<StudentDto> result = studentService.getStudentsByAgeRange(8, 12);

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(studentRepository).findByAgeBetweenOrderByAgeAsc(8, 12);
        }
        
        @Test
        @DisplayName("Should throw exception for invalid age range")
        public void testGetStudentsByInvalidAgeRange() {
            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                studentService.getStudentsByAgeRange(15, 10);
            });
            
            assertTrue(exception.getMessage().contains("minAge cannot be greater than maxAge"));
        }
    }
    
    @Nested
    @DisplayName("Bus Route Assignment Tests")
    class BusRouteAssignmentTests {
        
        @Test
        @DisplayName("Should assign bus route successfully")
        public void testAssignBusRoute() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
            when(studentRepository.save(any(Student.class))).thenReturn(mockStudent);

            // When
            StudentDto result = studentService.assignBusRoute(1L, "Route-A", "07:30", "15:30");

            // Then
            assertNotNull(result);
            verify(studentRepository).findById(1L);
            verify(studentRepository).save(any(Student.class));
        }
        
        @Test
        @DisplayName("Should throw exception for empty bus route")
        public void testAssignEmptyBusRoute() {
            // Given
            when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

            // When & Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                studentService.assignBusRoute(1L, "", "07:30", "15:30");
            });
            
            assertTrue(exception.getMessage().contains("Bus route cannot be empty"));
        }
    }
    
    @Nested
    @DisplayName("Statistics Tests")
    class StatisticsTests {
        
        @Test
        @DisplayName("Should get student count by grade")
        public void testGetStudentsCountByGrade() {
            // Given
            when(studentRepository.countByGrade("5th Grade")).thenReturn(5L);

            // When
            long result = studentService.getStudentsCountByGrade("5th Grade");

            // Then
            assertEquals(5L, result);
            verify(studentRepository).countByGrade("5th Grade");
        }
        
        @Test
        @DisplayName("Should get student count by bus route")
        public void testGetStudentsCountByBusRoute() {
            // Given
            when(studentRepository.countByBusRoute("Route-A")).thenReturn(10L);

            // When
            long result = studentService.getStudentsCountByBusRoute("Route-A");

            // Then
            assertEquals(10L, result);
            verify(studentRepository).countByBusRoute("Route-A");
        }
    }

    private Student createMockStudent() {
        return createMockStudent("John", "Doe", "STU001");
    }
    
    private Student createMockStudent(String firstName, String lastName, String studentId) {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setStudentId(studentId);
        student.setAge(10);
        student.setGrade("5th Grade");
        student.setAddress("123 Main Street, City, State 12345");
        student.setParentContact("5551234567");
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        return student;
    }
} 