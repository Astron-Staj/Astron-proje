package com.project.astron.dto;

import com.project.astron.model.Authority;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class TemplateUpdateDTO {

	
	long id;
	
	String name;
	
	Authority[] authority;
	
	boolean state;

	public TemplateUpdateDTO(long id, String name, Authority[] authority,boolean state) {
		super();
		this.id = id;
		this.name = name;
		this.authority = authority;
		this.state=state;
	}

	public TemplateUpdateDTO() {
		super();
	}
	
	
	
	
	
	
	
}
