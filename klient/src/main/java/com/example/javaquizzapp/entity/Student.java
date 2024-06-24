package com.example.javaquizzapp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Student {
    String index;
    String name;
    String lastName;
    String password;
    Roles role;
    private Long id;
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
