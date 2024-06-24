package com.example.javaquizzapp.repository;

import com.example.javaquizzapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIndex(String index);
    Optional<Student> findByIndexAndPassword(String index, String password);
}
