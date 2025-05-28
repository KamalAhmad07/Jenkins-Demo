package com.jenkins_demo.repository;

import com.jenkins_demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository  extends JpaRepository<Student, Integer> {
}
