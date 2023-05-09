package com.example.doctorcare;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoctorCareApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DoctorCareApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
