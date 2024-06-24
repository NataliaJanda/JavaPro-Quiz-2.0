package com.example.javaquizzapp.controller;

import com.example.javaquizzapp.entity.Student;
import com.example.javaquizzapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAllStudents();
    }

    @PostMapping("/register")
    public void registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
    }

    @PostMapping("/login")
    public Student loginStudent(@RequestParam String index, @RequestParam String password) {
        if (studentService.validateStudent(index, password)) {
            return studentService.getCurrentStudent();
        }
        throw new IllegalArgumentException("Invalid index or password");
    }
}
