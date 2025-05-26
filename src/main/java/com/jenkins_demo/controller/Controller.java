package com.jenkins_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.List.of;

@RestController
public class Controller {
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Jenkins Demo CI/CD!";
    }

    @GetMapping("/getUsers")
    public List<Student> getUsers() {
        List<Student> students = of(
                new Student(1,"Kamal","Rowing club Road Bhawanipur Motihari","India"),
                new Student(2,"Sidd","Chhatauni Colony Motihari ","India"),
                new Student(3,"Faiyaz","Raza Bazar Motihari","India"));
        return students;
    }
}
