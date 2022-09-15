package com.example.demo;

import com.example.demo.controller.Actions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("Bienvenido al CRM de NOMBRE");
		new Actions();
		SpringApplication.run(DemoApplication.class, args);
	}

}
