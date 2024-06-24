package com.example.javaquizzapp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Test {

    private Long id;
    String score;
    String maxScore;
    String grade;
    int shot;
    private Student student;

    public Test(Long id, int shot, String score, String MaxScore, String grade, Student student){
        this.id = id;
        this.shot = shot;
        this.score = score;
        this.maxScore = MaxScore;
        this.grade = grade;
        this.student = student;
    }

    public Test() {
    }
    public String getStudentIndex() {
        return student != null ? student.getIndex() : null;
    }
}
