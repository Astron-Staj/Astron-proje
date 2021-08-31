package com.project.astron.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.astron.model.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientUpdateDTO {

	
	String code;
	
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
