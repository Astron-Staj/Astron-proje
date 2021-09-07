package com.project.astron.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.project.astron.model.Client;
import com.project.astron.model.Template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserUpdateDTO {
	@NotBlank(message="Email alanı boş kalamaz.")
	 @Email(message = "Geçerli bir email giriniz.")
	public String email;
	
	@NotBlank(message="Ad alanı boş kalamaz")
	 @Size(min = 2, max = 30,message = "Adınız 2-30 karakterden oluşmalıdır.")
	public String firstName;

	
	public boolean state;
	
	@NotBlank(message="Soyad alanı boş kalamaz.")
	 @Size(min = 2, max = 30,message = "Soyadınız  2-30 karakterden oluşmalıdır.")
	public String lastName;

	@NotBlank(message = "Kullanıcı adı boş kalamaz.")
	@Size(min = 3, max = 50,message = "Kullanıcı adınız  3-50 karakterden oluşmalıdır.")
	public String username;
	
	
	public Template[] templates;
	
	
	public Client[] clients;
	

	public UserUpdateDTO(String email, String firstName, boolean state, String lastName, String username,
			Template[] templates,Client[] clients) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.state = state;
		this.lastName = lastName;
		this.username = username;
		this.templates = templates;
		this.clients=clients;
	}

	public UserUpdateDTO() {
		
	}

	
}
