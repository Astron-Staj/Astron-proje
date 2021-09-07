package com.project.astron.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SitemapUpdateDTO {

	@NotBlank(message="Bu alan boş bırakılamaz.")
	@URL(regexp = "^(http|ftp).*",message = "Geçerli bir URL giriniz.")
	@Size(max=100,message="URL karakter sınırını aştınız.")
	String url;
	long Id;
	
	
}
