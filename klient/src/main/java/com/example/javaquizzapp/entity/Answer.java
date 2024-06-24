package com.example.javaquizzapp.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Answer {
    private Long id;
    private String answer;
    private boolean correct;

    private Question question;

    public Answer() {
    }

    public Answer(Long id, String answer, boolean correct, Question question) {
        this.id = id;
        this.answer = answer;
        this.correct = correct;
        this.question = question;
    }
}
