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
public class TemplateDTO {

	@NotBlank(message="Ad alanı boş kalamaz")
	@Size(min = 2, max = 30,message = "Ad 2-30 karakterden oluşmalıdır.")
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
