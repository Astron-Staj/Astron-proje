package com.project.astron.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.project.astron.model.Authority;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class TemplateUpdateDTO {

	
	long id;
	
	@NotBlank(message="Ad alanı boş kalamaz")
	@Size(min = 2, max = 30,message = "Adınız 2-30 karakterden oluşmalıdır.")
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
