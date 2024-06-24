package com.example.javaquizzapp.controller;

import com.example.javaquizzapp.entity.Student;
import com.example.javaquizzapp.service.CurrentStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/current-student")
public class CurrentStudentController {

    private final CurrentStudentService currentStudentService;

    @Autowired
    public CurrentStudentController(CurrentStudentService currentStudentService) {
        this.currentStudentService = currentStudentService;
    }

    @GetMapping
    public Student getCurrentStudent() {
        return currentStudentService.getCurrentStudent();
    }

    @PostMapping("/logout")
    public void logoutCurrentStudent() {
        currentStudentService.clearCurrentStudent();
    }
}