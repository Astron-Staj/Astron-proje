package com.project.astron.dto;

import java.sql.Blob;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientCreateDTO {

	String code;
	
	String name;
	
	boolean state;
	
	MultipartFile logo;
	
}
