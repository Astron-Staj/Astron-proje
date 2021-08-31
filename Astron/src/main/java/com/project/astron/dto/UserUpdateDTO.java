package com.project.astron.dto;

import com.project.astron.model.Client;
import com.project.astron.model.Template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserUpdateDTO {
	
	public String email;
	
	public String firstName;

	public boolean state;
	
	public String lastName;

	public String username;
	
	public Template[] templates;
	
	public Client[] clients;
	

	public UserUpdateDTO(String email, String firstName, boolean state, String lastName, String username,
			Template[] templates,Client[] clients) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.state = state;
		this.lastName = lastName;
		this.username = username;
		this.templates = templates;
		this.clients=clients;
	}

	public UserUpdateDTO() {
		
	}

	
}
