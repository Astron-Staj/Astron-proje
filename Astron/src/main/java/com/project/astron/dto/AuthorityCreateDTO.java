package com.project.astron.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthorityCreateDTO {

	
	@NotBlank(message="Ad alanı boş kalamaz")
	@Size(min = 2, max = 30,message = "Ad 2-30 karakterden oluşmalıdır.")
	public String name;
	
	public boolean state;


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}


	public AuthorityCreateDTO(String name, boolean state) {
		super();
		this.name = name;
		this.state=state;
	
	}


	public AuthorityCreateDTO() {
		// TODO Auto-generated constructor stub
	}





	
	
	
	
	
	
	
}
