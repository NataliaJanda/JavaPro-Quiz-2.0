package com.example.javaquizzapp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question {
    String question;

    private Long id;

    private List<Answer> answers;
    private String imageData;

    public Question(Long id, String question){
        this.id = id;
        this.question = question;
    }
    public Question(){}

    @Override
    public String toString() {
        return question;
    }

}
