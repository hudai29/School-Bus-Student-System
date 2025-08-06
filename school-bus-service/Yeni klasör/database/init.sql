-- School Bus Service Database Initialization Script
-- This script creates the database and adds sample data

-- Create database (run this as superuser)
-- CREATE DATABASE school_bus_db;

-- Connect to the database
-- \c school_bus_db;

-- The table will be created automatically by Hibernate/JPA
-- But here's the manual creation script for reference:

/*
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
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/

-- Sample data insertion (run after the application has created the table)
-- You can use the API endpoints to add data, or run these inserts manually:

/*
INSERT INTO students (first_name, last_name, student_id, age, grade, address, parent_contact, bus_route, pickup_time, dropoff_time, created_at, updated_at) VALUES
('John', 'Doe', 'STU001', 10, '5th Grade', '123 Main Street, City, State 12345', '5551234567', 'Route-A', '07:30', '15:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Jane', 'Smith', 'STU002', 11, '6th Grade', '456 Oak Avenue, City, State 12345', '5552345678', 'Route-B', '07:45', '15:45', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mike', 'Johnson', 'STU003', 9, '4th Grade', '789 Pine Road, City, State 12345', '5553456789', 'Route-A', '07:30', '15:30', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sarah', 'Williams', 'STU004', 12, '7th Grade', '321 Elm Street, City, State 12345', '5554567890', 'Route-C', '08:00', '16:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('David', 'Brown', 'STU005', 10, '5th Grade', '654 Maple Drive, City, State 12345', '5555678901', 'Route-B', '07:45', '15:45', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
*/

-- Create indexes for better performance
-- CREATE INDEX idx_students_student_id ON students(student_id);
-- CREATE INDEX idx_students_grade ON students(grade);
-- CREATE INDEX idx_students_bus_route ON students(bus_route);
-- CREATE INDEX idx_students_age ON students(age);

-- Grant permissions (if needed)
-- GRANT ALL PRIVILEGES ON TABLE students TO your_username;
-- GRANT USAGE, SELECT ON SEQUENCE students_id_seq TO your_username; 