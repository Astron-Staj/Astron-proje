package com.project.astron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.project.astron.service","com.project.astron.controller","com.project.astron","com.project.astron.dto.mapper"})
@EntityScan("com.project.astron.model")
public class AstronApplication {

	public static void main(String[] args) {
		SpringApplication.run(AstronApplication.class, args);
	}

}
