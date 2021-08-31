package com.project.astron.dto.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.astron.dto.UserCreateDTO;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.service.ITemplateService;

public class UserCreateMapper {

	
	 BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder(10);

	 

	 
	 
    public List<Object> toEntity(UserCreateDTO dto) {
    	Date date=new Date();
        User user = new User(
        		dto.getEmail()
        		,dto.getFirstName(),dto.getLastName(),true, date, date);
      
        
        System.out.println(dto.getPassword());
        Credential credential=new Credential(dto.getUsername(),passwordEncoder.encode(dto.getPassword()));
        		
        List <Object> list=new ArrayList<Object>();
        
        list.add(user);
        list.add(credential);
        return list;
    }
}