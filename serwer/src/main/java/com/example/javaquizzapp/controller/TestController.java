package com.example.javaquizzapp.controller;

import com.example.javaquizzapp.entity.Test;
import com.example.javaquizzapp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public ResponseEntity<List<Test>> getAllTests() {
        List<Test> tests = testService.findAllTest();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Test>> getTestsByStudentId(@PathVariable Long studentId) {
        List<Test> tests = testService.findAllTestsByStudent(studentId);
        return tests != null ? ResponseEntity.ok(tests) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        Test createdTest = testService.saveTest(test);
        return ResponseEntity.ok(createdTest);
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Test> updateTest(@PathVariable Long id, @RequestBody Test updatedTest) {
//        Test test = testService.updateTest(id, updatedTest);
//        return test != null ? ResponseEntity.ok(test) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
//        boolean deleted = testService.deleteTest(id);
//        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }
}
