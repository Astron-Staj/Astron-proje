package com.project.astron.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.astron.model.Template;
import com.project.astron.service.ITemplateService;
@RestController
@RequestMapping("template")
public class TemplateController {

	@Autowired
	ITemplateService templateService;
	
	
	@GetMapping("/all")
	public List<Template> getAll() {
	
	return templateService.findAll();
	}
	
	
}
