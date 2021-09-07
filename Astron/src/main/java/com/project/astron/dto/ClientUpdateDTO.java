package com.project.astron.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.project.astron.model.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientUpdateDTO {

	@Pattern(regexp="^[0-9]*$",message="Kod alanı rakamlardan oluşmalıdır")
	@Length(min =4,max =4,message="kod  4 basamaklı olmalıdır ")
	@NotBlank(message="Kod alanı boş kalamaz")
	String code;
	
	@NotBlank(message="Ad alanı boş kalamaz")
	@Size(min = 2, max = 30,message = "Ad 2-30 karakterden oluşmalıdır.")
	String name;

	boolean state;
	
	

	public ClientUpdateDTO(String code, String name, boolean state) {
		super();
		this.code = code;
		this.name = name;
		this.state = state;

	}

	public ClientUpdateDTO() {
		super();
	}
	
	
	
	
	
}
