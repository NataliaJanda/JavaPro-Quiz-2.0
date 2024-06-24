package com.example.javaquizzapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String score;
    String MaxScore;
    String grade;
    int shot;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Test(Long id, int shot, String score, String MaxScore, String grade, Student student){
        this.id = id;
        this.shot = shot;
        this.score = score;
        this.MaxScore = MaxScore;
        this.grade = grade;
        this.student = student;
    }

    public Test() {
    }
    public String getStudentIndex() {
        return student != null ? student.getIndex() : null;
    }
}
