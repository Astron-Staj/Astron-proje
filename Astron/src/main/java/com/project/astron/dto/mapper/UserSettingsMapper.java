package com.project.astron.dto.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.astron.dto.UserSettingsDTO;
import com.project.astron.dto.UserUpdateDTO;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;

import lombok.Data;



@Data
public class UserSettingsMapper {

	
	 
    public List<Object> toEntity(UserSettingsDTO dto,User user,Credential cre,long updater) {
    	Date date=new Date();
    	
   
    	
    
    	
    	User newUser=new User(user.getId(),dto.getEmail(),dto.getFirstName(),dto.getLastName(),
    			dto.isState(),user.getLastLogin(),
    			user.getCreated(),date,user.getCreator(),
    			updater);
    	newUser.setTemplates(user.getTemplates());
       Credential credential= new Credential(cre.getId(),dto.getUsername(),cre.getPassword(),cre.getUserId(),newUser);
        cre.setUsername(dto.getUsername());
    	
        List <Object> list=new ArrayList<Object>();
        
        list.add(newUser);
        list.add(credential);
        return list;
    }
}
