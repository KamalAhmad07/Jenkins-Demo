package com.jenkins_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JenkinsDemoApplication {
	public static void main(String[] args) {
		System.err.println("Starting....");
		SpringApplication.run(JenkinsDemoApplication.class, args);
		System.err.println("Started....");

	}
}
