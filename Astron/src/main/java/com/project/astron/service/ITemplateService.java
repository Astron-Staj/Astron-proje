package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Template;


public interface ITemplateService  {
	
	List<Template>  findAll();
	List<Template>  findActiveInactive();
 	Template findByName(String name);
	Template findById(long id) throws Exception; 
	Template saveTemplate (Template cre) throws Exception;
	void deleteTemplate (Template template) throws Exception  ;
	Template updateTemplate (Template template) throws Exception ;
	Template deleteUpdateTemplate (Template template) throws Exception ;
	Optional<Template> findTemplate(long id);
	void deleteTemplateById(long id) throws Exception;
	
}
