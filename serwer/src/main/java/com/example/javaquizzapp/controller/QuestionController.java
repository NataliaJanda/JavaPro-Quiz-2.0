package com.example.javaquizzapp.controller;

import com.example.javaquizzapp.entity.Question;
import com.example.javaquizzapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public void addQuestion(@RequestBody Question question) {
        questionService.saveQuestion(question);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestionsWithAnswers();
    }

    @GetMapping("/random")
    public List<Question> getRandomQuestions(@RequestParam int number) {
        return questionService.getRandomQuestions(number);
    }
}