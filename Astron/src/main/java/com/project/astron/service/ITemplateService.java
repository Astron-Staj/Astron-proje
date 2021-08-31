package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Template;


public interface ITemplateService  {
	
	List<Template>  findAll();
	Template findByName(String name);
	Template findById(long id);
	//Template createTemplate (Template cre) throws Exception;
	void deleteTemplate (Template template) throws Exception  ;
	void updateTemplate (Template template) ;
	Optional<Template> findTemplate(long id);
	void deleteTemplateById(long id);
	
}
