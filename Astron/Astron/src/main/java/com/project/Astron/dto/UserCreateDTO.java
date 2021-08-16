package com.project.astron.dto;

import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserCreateDTO {
	
	public String email;
	
	public String firstName;

	public String lastName;

	public String username;
	
	public String password;

	
	
	public UserCreateDTO(String email, String firstName, String lastName, String username, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}


	
	
	

	
	
	
	
	
	
}


