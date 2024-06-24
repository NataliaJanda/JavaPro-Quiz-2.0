package com.example.javaquizzapp.service;

import com.example.javaquizzapp.entity.Answer;
import com.example.javaquizzapp.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> getAllAnswer(){
        return answerRepository.findAll();
    }
}
