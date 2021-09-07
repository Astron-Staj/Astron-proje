package com.project.astron.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthorityUpdateDTO {

	
	public long id;
	
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




	public AuthorityUpdateDTO(long id, String name, boolean state) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
	}


	public AuthorityUpdateDTO() {
		// TODO Auto-generated constructor stub
	}







	
	
	
	
	
	
	
}
