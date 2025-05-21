package com.jenkins_demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {
     private int id;
     private String  name;
     private String address;
     private String country;
}
