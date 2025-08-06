package com.schoolbus.domain.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class StudentDto {
    
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @NotBlank(message = "Student ID is required")
    @Size(min = 5, max = 20, message = "Student ID must be between 5 and 20 characters")
    private String studentId;
    
    @NotNull(message = "Age is required")
    @Min(value = 3, message = "Age must be at least 3")
    @Max(value = 18, message = "Age must be at most 18")
    private Integer age;
    
    @NotBlank(message = "Grade is required")
    private String grade;
    
    @NotBlank(message = "Address is required")
    @Size(min = 10, max = 200, message = "Address must be between 10 and 200 characters")
    private String address;
    
    @NotBlank(message = "Parent contact is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Parent contact must be a valid phone number")
    private String parentContact;
    
    private String busRoute;
    private String pickupTime;
    private String dropoffTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Default constructor
    public StudentDto() {}
    
    // Constructor with all fields
    public StudentDto(Long id, String firstName, String lastName, String studentId, 
                     Integer age, String grade, String address, String parentContact,
                     String busRoute, String pickupTime, String dropoffTime,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.age = age;
        this.grade = grade;
        this.address = address;
        this.parentContact = parentContact;
        this.busRoute = busRoute;
        this.pickupTime = pickupTime;
        this.dropoffTime = dropoffTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Constructor for creating new student
    public StudentDto(String firstName, String lastName, String studentId, 
                     Integer age, String grade, String address, String parentContact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.age = age;
        this.grade = grade;
        this.address = address;
        this.parentContact = parentContact;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getParentContact() {
        return parentContact;
    }
    
    public void setParentContact(String parentContact) {
        this.parentContact = parentContact;
    }
    
    public String getBusRoute() {
        return busRoute;
    }
    
    public void setBusRoute(String busRoute) {
        this.busRoute = busRoute;
    }
    
    public String getPickupTime() {
        return pickupTime;
    }
    
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
    
    public String getDropoffTime() {
        return dropoffTime;
    }
    
    public void setDropoffTime(String dropoffTime) {
        this.dropoffTime = dropoffTime;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", age=" + age +
                ", grade='" + grade + '\'' +
                ", address='" + address + '\'' +
                ", parentContact='" + parentContact + '\'' +
                ", busRoute='" + busRoute + '\'' +
                ", pickupTime='" + pickupTime + '\'' +
                ", dropoffTime='" + dropoffTime + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 