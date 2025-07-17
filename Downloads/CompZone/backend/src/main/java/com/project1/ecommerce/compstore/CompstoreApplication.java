package com.project1.ecommerce.compstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.project1.ecommerce.compstore")
public class CompstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompstoreApplication.class, args);
	}

}
