package com.project.astron.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProjectCreateDTO {
	
	
	@NotBlank(message="Ad alanı boş kalamaz")
	@Size(min = 2, max = 30,message = "Ad 2-30 karakterden oluşmalıdır.")
	String projectName;
	
	@Positive(message = "Pozitif bir değer giriniz.") 
	@Min(value = 0,message = "Geçerli bir değer giriniz.")
    @Max(value = 100_000_000_0,message="Geçerli bir değer giriniz")
	long taskCount;
	
	long siteMapId;
	
	

}
