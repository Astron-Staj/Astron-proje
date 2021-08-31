package com.project.astron.dto.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.astron.dto.TemplateUpdateDTO;
import com.project.astron.dto.UserUpdateDTO;
import com.project.astron.model.Authority;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;

public class TemplateUpdateMapper {

    public Template toEntity(TemplateUpdateDTO dto,long updater,Template temp) {
    	Date date=new Date();
    	List <Authority> authList =new  ArrayList<Authority>();
    	for (Authority auth : dto.getAuthority()) {
				authList.add(auth);
		
		}
    	
    
    	
    	Template newTemplate=new Template(dto.getId(),dto.getName(),dto.isState(),authList,temp.getCreated(),date,updater,temp.getCreator());
    	
        for (Authority auth : newTemplate.getAuths()) {
			System.out.println(auth.name);
			
	}
       
        
        return newTemplate;
    }
	
	
	
	
	
	
	
}
