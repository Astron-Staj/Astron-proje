package com.project.astron.dto.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.astron.dto.TemplateDTO;
import com.project.astron.dto.UserCreateDTO;
import com.project.astron.model.Authority;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;

public class TemplateCreateMapper {
	 
	 
    public Template toEntity(TemplateDTO dto,long creator) {
    	Date date=new Date();
        Template template = new Template(dto.getName(),dto.state,date,creator);
        for (Authority auth : dto.getAuthority()) {
        	template.getAuths().add(auth);
			
		}
        
        return template;
    }
}
