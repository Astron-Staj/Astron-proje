package com.project.astron.dto;



public class AuthorityCreateDTO {

	
	
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}




	
	
	
	
	
	
	
}
