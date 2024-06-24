package com.example.javaquizzapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "student")
public class Student {
    String index;
    String name;
    String lastName;
    String password;
    Roles role;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Test> test;

    public Student(Long id,String index, String name, String lastName, String password, Roles role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.index = index;
        this.password = password;
        this.role = role;
    }

    public Student() {
    }
}
