package com.project.astron.dto.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.project.astron.dto.AuthorityCreateDTO;
import com.project.astron.model.Authority;

public class AuthorityCreateMapper {

	
	
	 public List<Object> toEntity(AuthorityCreateDTO dto) {
	    	Date date=new Date();
	    	
	    	Authority auth = new Authority(dto.isState(),dto.getName(), date, date);
	      
	        List <Object> list=new ArrayList<Object>();
	        list.add(auth);
	        return list;
	 }
	
	
}
