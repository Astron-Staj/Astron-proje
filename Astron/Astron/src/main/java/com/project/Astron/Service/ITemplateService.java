package com.project.astron.service;

import java.util.List;

import com.project.astron.model.Template;


public interface ITemplateService  {
	
	List<Template>  findAll();
	Template findByName(String name);
	/*Template createTemplate (Template cre) throws Exception;
	void deleteTemplate (String name) throws Exception  ;
	void deleteTempla (String name) throws Exception  ;
	void updateTemplate (String name) throws Exception;
	long   CredentialCount ();*/
	
}
