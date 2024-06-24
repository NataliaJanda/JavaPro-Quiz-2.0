package com.example.javaquizzapp.service;

import com.example.javaquizzapp.entity.Student;
import com.example.javaquizzapp.repository.StudentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    private final StudentRepository studentRepository;
    private final CurrentStudentService currentStudentService;

    @Getter
    public Student currentStudent;

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Autowired
    public StudentService(StudentRepository studentRepository, CurrentStudentService currentStudentService) {
        this.studentRepository = studentRepository;
        this.currentStudentService = currentStudentService;
    }

    public void registerStudent(Student student) {
        //TODO:walidacja hasła
        studentRepository.save(student);
        currentStudentService.setCurrentStudent(student);

    }

    public boolean validateStudent(String index, String password) {
        Optional<Student> studentOptional = studentRepository.findByIndexAndPassword(index, password);
        if (studentOptional.isPresent()) {
            Student currentStudent = studentOptional.get();
            currentStudentService.setCurrentStudent(currentStudent);

            return true;
        }
        return false;
    }
}
