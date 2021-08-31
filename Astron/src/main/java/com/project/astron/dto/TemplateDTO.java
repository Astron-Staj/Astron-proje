package com.project.astron.dto;



import com.project.astron.model.Authority;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TemplateDTO {

	
	public String name;
	public Authority[] authority;
	public boolean state;
	
	
	public TemplateDTO(String name, Authority[] authority,boolean state) {
		
		super();
		this.name = name;
		this.authority = authority;
		this.state=state;
	}


	public TemplateDTO() {
		super();
	}
	
	
	
}
