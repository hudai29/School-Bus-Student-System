package com.schoolbus.application.service.impl;

import com.schoolbus.application.service.StudentService;
import com.schoolbus.domain.dto.StudentDto;
import com.schoolbus.domain.entity.Student;
import com.schoolbus.infrastructure.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of StudentService interface.
 * Contains business logic for student management operations.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        // Validate student data
        validateStudentData(studentDto);
        
        // Check if student ID already exists
        if (studentRepository.existsByStudentId(studentDto.getStudentId())) {
            throw new IllegalArgumentException("Student with ID '" + studentDto.getStudentId() + "' already exists");
        }
        
        // Convert DTO to Entity
        Student student = convertToEntity(studentDto);
        
        // Save the student
        Student savedStudent = studentRepository.save(student);
        
        // Convert back to DTO and return
        return convertToDto(savedStudent);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAllByOrderByFirstNameAsc();
        return students.stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        return convertToDto(student);
    }
    
    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentByStudentId(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with Student ID: " + studentId));
        return convertToDto(student);
    }
    
    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        // Validate student data
        validateStudentData(studentDto);
        
        // Find existing student
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        
        // Check for student ID conflicts (only if student ID is being changed)
        if (!existingStudent.getStudentId().equals(studentDto.getStudentId())) {
            if (studentRepository.existsByStudentId(studentDto.getStudentId())) {
                throw new IllegalArgumentException("Student with ID '" + studentDto.getStudentId() + "' already exists");
            }
        }
        
        // Update entity fields
        updateEntityFromDto(existingStudent, studentDto);
        
        // Save the updated student
        Student updatedStudent = studentRepository.save(existingStudent);
        
        // Convert back to DTO and return
        return convertToDto(updatedStudent);
    }
    
    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByGrade(String grade) {
        List<Student> students = studentRepository.findByGradeOrderByFirstNameAsc(grade);
        return students.stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByBusRoute(String busRoute) {
        List<Student> students = studentRepository.findByBusRouteOrderByPickupTimeAsc(busRoute);
        return students.stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> searchStudentsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllStudents();
        }
        
        List<Student> students = studentRepository.searchByName(name.trim());
        return students.stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByAgeRange(Integer minAge, Integer maxAge) {
        if (minAge == null || maxAge == null) {
            throw new IllegalArgumentException("Both minAge and maxAge must be provided");
        }
        
        if (minAge > maxAge) {
            throw new IllegalArgumentException("minAge cannot be greater than maxAge");
        }
        
        List<Student> students = studentRepository.findByAgeBetweenOrderByAgeAsc(minAge, maxAge);
        return students.stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }
    
    @Override
    public StudentDto assignBusRoute(Long id, String busRoute, String pickupTime, String dropoffTime) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        
        // Validate bus route assignment data
        if (busRoute == null || busRoute.trim().isEmpty()) {
            throw new IllegalArgumentException("Bus route cannot be empty");
        }
        if (pickupTime == null || pickupTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Pickup time cannot be empty");
        }
        if (dropoffTime == null || dropoffTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Dropoff time cannot be empty");
        }
        
        // Update bus route information
        student.setBusRoute(busRoute.trim());
        student.setPickupTime(pickupTime.trim());
        student.setDropoffTime(dropoffTime.trim());
        
        // Save the updated student
        Student updatedStudent = studentRepository.save(student);
        
        return convertToDto(updatedStudent);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getStudentsCountByGrade(String grade) {
        return studentRepository.countByGrade(grade);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getStudentsCountByBusRoute(String busRoute) {
        return studentRepository.countByBusRoute(busRoute);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllGrades() {
        return studentRepository.findAllDistinctGrades();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllBusRoutes() {
        return studentRepository.findAllDistinctBusRoutes();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsWithoutBusRoute() {
        List<Student> students = studentRepository.findByBusRouteIsNullOrderByFirstNameAsc();
        return students.stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }
    
    @Override
    public void validateStudentData(StudentDto studentDto) {
        if (studentDto == null) {
            throw new IllegalArgumentException("Student data cannot be null");
        }
        
        // Additional business validation beyond bean validation
        if (studentDto.getFirstName() != null && studentDto.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        
        if (studentDto.getLastName() != null && studentDto.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        
        if (studentDto.getStudentId() != null && studentDto.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        
        if (studentDto.getGrade() != null && studentDto.getGrade().trim().isEmpty()) {
            throw new IllegalArgumentException("Grade cannot be empty");
        }
        
        if (studentDto.getAddress() != null && studentDto.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        
        if (studentDto.getParentContact() != null && studentDto.getParentContact().trim().isEmpty()) {
            throw new IllegalArgumentException("Parent contact cannot be empty");
        }
        
        // Validate age range
        if (studentDto.getAge() != null && (studentDto.getAge() < 3 || studentDto.getAge() > 18)) {
            throw new IllegalArgumentException("Age must be between 3 and 18");
        }
    }
    
    /**
     * Converts Student entity to StudentDto
     * @param student the student entity
     * @return the student DTO
     */
    private StudentDto convertToDto(Student student) {
        if (student == null) {
            return null;
        }
        
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setStudentId(student.getStudentId());
        dto.setAge(student.getAge());
        dto.setGrade(student.getGrade());
        dto.setAddress(student.getAddress());
        dto.setParentContact(student.getParentContact());
        dto.setBusRoute(student.getBusRoute());
        dto.setPickupTime(student.getPickupTime());
        dto.setDropoffTime(student.getDropoffTime());
        dto.setCreatedAt(student.getCreatedAt());
        dto.setUpdatedAt(student.getUpdatedAt());
        
        return dto;
    }
    
    /**
     * Converts StudentDto to Student entity
     * @param studentDto the student DTO
     * @return the student entity
     */
    private Student convertToEntity(StudentDto studentDto) {
        if (studentDto == null) {
            return null;
        }
        
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setStudentId(studentDto.getStudentId());
        student.setAge(studentDto.getAge());
        student.setGrade(studentDto.getGrade());
        student.setAddress(studentDto.getAddress());
        student.setParentContact(studentDto.getParentContact());
        student.setBusRoute(studentDto.getBusRoute());
        student.setPickupTime(studentDto.getPickupTime());
        student.setDropoffTime(studentDto.getDropoffTime());
        
        return student;
    }
    
    /**
     * Updates Student entity from StudentDto
     * @param student the student entity to update
     * @param studentDto the student DTO with new data
     */
    private void updateEntityFromDto(Student student, StudentDto studentDto) {
        if (student == null || studentDto == null) {
            return;
        }
        
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setStudentId(studentDto.getStudentId());
        student.setAge(studentDto.getAge());
        student.setGrade(studentDto.getGrade());
        student.setAddress(studentDto.getAddress());
        student.setParentContact(studentDto.getParentContact());
        student.setBusRoute(studentDto.getBusRoute());
        student.setPickupTime(studentDto.getPickupTime());
        student.setDropoffTime(studentDto.getDropoffTime());
    }
}