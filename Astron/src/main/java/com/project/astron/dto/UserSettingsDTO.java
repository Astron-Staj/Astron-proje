package com.project.astron.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.project.astron.model.Template;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSettingsDTO {
	@NotBlank(message="Email alanı boş kalamaz.")
	 @Email(message = "Geçerli bir email giriniz.")
	public String email;
	
	@NotBlank(message="Ad alanı boş kalamaz")
	 @Size(min = 2, max = 30,message = "Adınız 2-30 karakterden oluşmalıdır.")
	public String firstName;

	@NotBlank(message="Soyad alanı boş kalamaz.")
	 @Size(min = 2, max = 30,message = "Soyadınız  2-30 karakterden oluşmalıdır.")
	public String lastName;

	@NotBlank(message = "Kullanıcı adı boş kalamaz.")
	@Size(min = 3, max = 50,message = "Kullanıcı adınız  3-50 karakterden oluşmalıdır.")
	public String username;

	public UserSettingsDTO(String email, String firstName,  String lastName, String username) {
		super();
		this.email = email;
		this.firstName = firstName;
		
		this.lastName = lastName;
		this.username = username;
	}

	public UserSettingsDTO() {
		super();
	}
	
	
	

	

	
}
