package com.schoolbus.interfaces.controller;

import com.schoolbus.application.service.StudentService;
import com.schoolbus.domain.dto.StudentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Student Management", description = "APIs for managing school bus students")
public class StudentController {
    
    private final StudentService studentService;
    
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new student", 
               description = "Creates a new student with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Student created successfully",
                    content = @Content(schema = @Schema(implementation = StudentDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Student with same ID already exists")
    })
    public ResponseEntity<StudentDto> createStudent(
            @Parameter(description = "Student information to create", required = true)
            @Valid @RequestBody StudentDto studentDto) {
        StudentDto createdStudent = studentService.createStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }
    
    @GetMapping
    @Operation(summary = "Get all students", 
               description = "Retrieves a list of all students ordered by first name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students",
                    content = @Content(schema = @Schema(implementation = StudentDto.class)))
    })
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", 
               description = "Retrieves a specific student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student",
                    content = @Content(schema = @Schema(implementation = StudentDto.class))),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentDto> getStudentById(
            @Parameter(description = "Student ID", required = true)
            @PathVariable Long id) {
        StudentDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    @GetMapping("/student-id/{studentId}")
    @Operation(summary = "Get student by student ID", 
               description = "Retrieves a specific student by their student ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student",
                    content = @Content(schema = @Schema(implementation = StudentDto.class))),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentDto> getStudentByStudentId(
            @Parameter(description = "Student ID", required = true)
            @PathVariable String studentId) {
        StudentDto student = studentService.getStudentByStudentId(studentId);
        return ResponseEntity.ok(student);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update student", 
               description = "Updates an existing student with new information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = @Content(schema = @Schema(implementation = StudentDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "409", description = "Student ID conflict")
    })
    public ResponseEntity<StudentDto> updateStudent(
            @Parameter(description = "Student ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated student information", required = true)
            @Valid @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
        return ResponseEntity.ok(updatedStudent);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", 
               description = "Deletes a student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "Student ID", required = true)
            @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/grade/{grade}")
    @Operation(summary = "Get students by grade", 
               description = "Retrieves all students in a specific grade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students",
                    content = @Content(schema = @Schema(implementation = StudentDto.class)))
    })
    public ResponseEntity<List<StudentDto>> getStudentsByGrade(
            @Parameter(description = "Grade level", required = true)
            @PathVariable String grade) {
        List<StudentDto> students = studentService.getStudentsByGrade(grade);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/bus-route/{busRoute}")
    @Operation(summary = "Get students by bus route", 
               description = "Retrieves all students assigned to a specific bus route")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students",
                    content = @Content(schema = @Schema(implementation = StudentDto.class)))
    })
    public ResponseEntity<List<StudentDto>> getStudentsByBusRoute(
            @Parameter(description = "Bus route", required = true)
            @PathVariable String busRoute) {
        List<StudentDto> students = studentService.getStudentsByBusRoute(busRoute);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search students by name", 
               description = "Searches for students by first name or last name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students",
                    content = @Content(schema = @Schema(implementation = StudentDto.class)))
    })
    public ResponseEntity<List<StudentDto>> searchStudentsByName(
            @Parameter(description = "Name to search for", required = true)
            @RequestParam String name) {
        List<StudentDto> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/age-range")
    @Operation(summary = "Get students by age range", 
               description = "Retrieves students within a specific age range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students",
                    content = @Content(schema = @Schema(implementation = StudentDto.class)))
    })
    public ResponseEntity<List<StudentDto>> getStudentsByAgeRange(
            @Parameter(description = "Minimum age", required = true)
            @RequestParam Integer minAge,
            @Parameter(description = "Maximum age", required = true)
            @RequestParam Integer maxAge) {
        List<StudentDto> students = studentService.getStudentsByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(students);
    }
    
    @PutMapping("/{id}/assign-bus-route")
    @Operation(summary = "Assign bus route to student", 
               description = "Assigns a bus route, pickup time, and dropoff time to a student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bus route assigned successfully",
                    content = @Content(schema = @Schema(implementation = StudentDto.class))),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentDto> assignBusRoute(
            @Parameter(description = "Student ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Bus route", required = true)
            @RequestParam String busRoute,
            @Parameter(description = "Pickup time", required = true)
            @RequestParam String pickupTime,
            @Parameter(description = "Dropoff time", required = true)
            @RequestParam String dropoffTime) {
        StudentDto updatedStudent = studentService.assignBusRoute(id, busRoute, pickupTime, dropoffTime);
        return ResponseEntity.ok(updatedStudent);
    }
    
    @GetMapping("/count/grade/{grade}")
    @Operation(summary = "Get student count by grade", 
               description = "Returns the number of students in a specific grade")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved count")
    })
    public ResponseEntity<Long> getStudentsCountByGrade(
            @Parameter(description = "Grade level", required = true)
            @PathVariable String grade) {
        long count = studentService.getStudentsCountByGrade(grade);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/count/bus-route/{busRoute}")
    @Operation(summary = "Get student count by bus route", 
               description = "Returns the number of students assigned to a specific bus route")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved count")
    })
    public ResponseEntity<Long> getStudentsCountByBusRoute(
            @Parameter(description = "Bus route", required = true)
            @PathVariable String busRoute) {
        long count = studentService.getStudentsCountByBusRoute(busRoute);
        return ResponseEntity.ok(count);
    }
} 