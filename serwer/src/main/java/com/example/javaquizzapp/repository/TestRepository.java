package com.example.javaquizzapp.repository;

import com.example.javaquizzapp.entity.Student;
import com.example.javaquizzapp.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByStudent(Student student);

}
