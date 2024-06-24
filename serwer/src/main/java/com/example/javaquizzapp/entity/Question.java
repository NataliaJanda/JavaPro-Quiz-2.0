package com.example.javaquizzapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Question {
    @Column (nullable = false)
    String question;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnore
    private List<Answer> answers;
    @Lob
    private String imageData;

    public Question(Long id, String question,String imageData){
        this.id = id;
        this.question = question;
        this.imageData = imageData;
    }
    public Question(){}

//    @Override
//    public String toString() {
//        return question;
//    }

}
