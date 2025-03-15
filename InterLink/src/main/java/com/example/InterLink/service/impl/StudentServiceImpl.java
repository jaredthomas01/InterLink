package com.example.InterLink.service.impl;

import com.example.InterLink.entity.StudentEntity;
import com.example.InterLink.repository.StudentRepository;
import com.example.InterLink.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<StudentEntity> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public StudentEntity saveStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    @Override
    public StudentEntity updateStudent(Long id, StudentEntity studentEntity) {
        return studentRepository.findById(id).map(existingStudent -> {
            existingStudent.setName(studentEntity.getName());
            existingStudent.setRegistrationNumber(studentEntity.getRegistrationNumber());
            return studentRepository.save(existingStudent);
        }).orElse(null);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
