package com.jenkins_demo.controller;

import com.jenkins_demo.entity.Student;
import com.jenkins_demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.List.of;

@RestController
public class Controller {


     @Autowired
     StudentRepository repository;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Jenkins Demo  ffffff CI/CD!";
    }

    @GetMapping("/getUsers")
    public List<Student> getUsers() {
        System.err.println("Adding new Student in the ");
        Student  s1 = new Student("Kamal","Rowing club Road Bhawanipur Motihari","India");
        Student  s2 = new Student("Sidd","Chhatauni Colony Motihari ","India");
        Student  s3 = new Student("Faiyaz","Raza Bazar Motihari","India");

       List<Student> students = List.of(s1,s2,s3);
       List<Student>  students1 = repository.saveAll(students);
        System.err.println("srudents1 ==1 " + students1);
        return students1;
    }
}
