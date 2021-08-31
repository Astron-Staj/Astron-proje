package com.project.astron.dto;

import com.project.astron.model.Template;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSettingsDTO {
	
	public String email;
	
	public String firstName;

	public boolean state;
	
	public String lastName;

	public String username;

	public UserSettingsDTO(String email, String firstName, boolean state, String lastName, String username) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.state = state;
		this.lastName = lastName;
		this.username = username;
	}

	public UserSettingsDTO() {
		super();
	}
	
	
	

	

	
}
