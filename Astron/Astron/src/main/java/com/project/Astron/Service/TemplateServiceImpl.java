package com.project.astron.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Template;
import com.project.astron.repository.TemplateDataRepository;

@Service
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	TemplateDataRepository templateDataRepository;
	
	
	@Override
	public List<Template> findAll() {

		List<Template> temps=templateDataRepository.findAll();
		
		return temps;
	}


	@Override
	public Template findByName(String name) {
	
	return templateDataRepository.findByName(name);
	}



	
	
	
	
}
