package com.project.astron.service;

import java.util.List;
import java.util.Optional;

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


	@Override
	public void deleteTemplate(Template template) throws Exception {
		templateDataRepository.delete(template);
		
	}


	@Override
	public Optional<Template> findTemplate(long id) {
		
		Optional<Template> template =templateDataRepository.findById(id);
		return	 template;
	
	}


	@Override
	public void updateTemplate(Template template)  {
		
		templateDataRepository.save(template);
	}


	@Override
	public void deleteTemplateById(long id) {
		templateDataRepository.deleteById(id);
	}


	@Override
	public Template findById(long id) {
		
		return templateDataRepository.getById(id);
	}



	

	
	
	
	
}
