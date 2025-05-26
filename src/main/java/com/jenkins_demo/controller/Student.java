package com.jenkins_demo.controller;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     private String  name;
     private String address;
     private String country;

     public Student(int id, String name, String address, String country) {
          this.id = id;
          this.name = name;
          this.address = address;
          this.country = country;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }

     public String getCountry() {
          return country;
     }

     public void setCountry(String country) {
          this.country = country;
     }

     @Override
     public String toString() {
          return "Student{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", address='" + address + '\'' +
                  ", country='" + country + '\'' +
                  '}';
     }
}
