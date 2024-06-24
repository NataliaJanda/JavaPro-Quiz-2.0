package com.example.javaquizzapp.controller;

import com.example.javaquizzapp.entity.Answer;
import com.example.javaquizzapp.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswer();
        return ResponseEntity.ok(answers);
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Answer> getAnswerById(@PathVariable Long id) {
//        Answer answer = answerService.getAnswerById(id);
//        return answer != null ? ResponseEntity.ok(answer) : ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) {
//        Answer createdAnswer = answerService.createAnswer(answer);
//        return ResponseEntity.ok(createdAnswer);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody Answer updatedAnswer) {
//        Answer answer = answerService.updateAnswer(id, updatedAnswer);
//        return answer != null ? ResponseEntity.ok(answer) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
//        boolean deleted = answerService.deleteAnswer(id);
//        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }
}
