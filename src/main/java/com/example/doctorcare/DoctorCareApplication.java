package com.example.doctorcare;

import com.example.doctorcare.api.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoctorCareApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DoctorCareApplication.class, args);
	}

	@Autowired
	private UserDetailsServiceImpl userDetailsService;


	@Override
	public void run(String... args) throws Exception {
	}
}
