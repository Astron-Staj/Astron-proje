package com.project.astron.dto;

import java.sql.Blob;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientCreateDTO {

	@Pattern(regexp="^[0-9]*$",message="Kod alanı rakamlardan oluşmalıdır")
	@Length(min =4,max =4,message="kod  4 basamaklı olmalıdır ")
	@NotBlank(message="Kod alanı boş kalamaz")
	String code;
	
	@NotBlank(message="Ad alanı boş kalamaz")
	@Size(min = 2, max = 30,message = "Ad 2-30 karakterden oluşmalıdır.")
	String name;
	
	boolean state;
	
	MultipartFile logo;
	
}
