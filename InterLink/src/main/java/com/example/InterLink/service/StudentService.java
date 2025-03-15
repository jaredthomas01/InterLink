package com.example.InterLink.service;

import com.example.InterLink.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentEntity> getAllStudents();
    Optional<StudentEntity> getStudentById(Long id);
    StudentEntity saveStudent(StudentEntity studentEntity);
    StudentEntity updateStudent(Long id, StudentEntity studentEntity);
    void deleteStudent(Long id);
}
