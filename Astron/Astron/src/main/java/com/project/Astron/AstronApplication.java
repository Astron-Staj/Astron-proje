package com.project.Astron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.project.Astron.Service","com.project.Astron.Controller"})
@EntityScan("com.project.Astron.Model")
public class AstronApplication {

	public static void main(String[] args) {
		SpringApplication.run(AstronApplication.class, args);
	}

}
