package com.project.astron.dto.mapper;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.astron.dto.ClientCreateDTO;
import com.project.astron.dto.UserCreateDTO;
import com.project.astron.model.Client;
import com.project.astron.model.Credential;
import com.project.astron.model.User;

public class ClientCreateMapper {

	
	public Client toEntity(ClientCreateDTO dto,User user,MultipartFile file) throws IOException {
    	Date date=new Date();
    	
    	
    
	
	
   
       Client client = new Client(
        		dto.getCode(),dto.isState()
        		,dto.getName(),file.getBytes(),date, date,user.getId(),user.getId());
      

        
        
        
        return client;
    }
}
