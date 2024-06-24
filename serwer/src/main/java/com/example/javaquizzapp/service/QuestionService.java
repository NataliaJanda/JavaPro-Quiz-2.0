package com.example.javaquizzapp.service;

import com.example.javaquizzapp.entity.Question;
import com.example.javaquizzapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {



    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    private QuestionRepository questionRepository;

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getAllQuestionsWithAnswers() {
        List<Question> questions = questionRepository.findAll();
        for (Question question : questions) {
            question.getAnswers().size();
        }
        return questions;
    }
    public List<Question> getRandomQuestions(int numberOfQuestions) {
        List<Question> allQuestions = questionRepository.findAll();
        Collections.shuffle(allQuestions);
        return allQuestions.subList(0, Math.min(numberOfQuestions, allQuestions.size()));
    }
}
