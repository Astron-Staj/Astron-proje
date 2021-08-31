package com.project.astron.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.astron.dto.TemplateDTO;
import com.project.astron.dto.TemplateUpdateDTO;
import com.project.astron.dto.UserCreateDTO;
import com.project.astron.dto.UserUpdateDTO;
import com.project.astron.dto.mapper.TemplateCreateMapper;
import com.project.astron.dto.mapper.TemplateUpdateMapper;
import com.project.astron.dto.mapper.UserCreateMapper;
import com.project.astron.dto.mapper.UserUpdateMapper;
import com.project.astron.model.Authority;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.service.IAuthorityService;
import com.project.astron.service.ICredentialService;
import com.project.astron.service.ITemplateService;
@Controller
@RequestMapping("template")
public class TemplateController {

	@Autowired
	ITemplateService templateService;
	
	@Autowired
	IAuthorityService authorityService;
	
	@Autowired
	ICredentialService credentialService;
	
	//@PreAuthorize("hasAuthority('TEMPLATE_READ_ALL')")
	@GetMapping("/all")
	public ModelAndView getAll(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("templates",templateService.findAll());
		mav.setViewName("templateRead");
		return mav;
	}
	
	@Transactional
	@GetMapping("process_delete/{id}")
	public String delete(@PathVariable(value="id")long id) {
		try {
			System.out.println(id);
			

			Optional<Template> template=templateService.findTemplate(id);
		
			
			 template.get().removeAuths();
			 template.get().removeUsers();

			templateService.deleteTemplate(template.get());
			
			return "redirect:/template/manageTemplate";
		} catch (Exception e) {
			
			return e.getMessage();
		}

	}
	


	
	@RequestMapping("/update/{id}")
	public ModelAndView showEditPage(@PathVariable(value = "id") long id,Authentication authentication) {
		String username=authentication.getName();
	    ModelAndView mav = new ModelAndView("templateUpdateDTO");
	    TemplateUpdateDTO templateUpdateDTO=new TemplateUpdateDTO();
	    Template template=templateService.findById(id);
	   templateUpdateDTO.setId(template.getId());
	   templateUpdateDTO.setName(template.getName());
	   templateUpdateDTO.setState(template.isState());
	   Authority[] authList =new Authority[template.getAuths().size()];
   		for(int i=0;i<template.getAuths().size();i++) {
   			authList[i]=template.getAuths().get(i);
   		}
	   	
	   templateUpdateDTO.setAuthority(authList);
	   mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
	   mav.addObject("authorities",authorityService.findAll());
	   mav.addObject("template",template);
	    mav.addObject("templateUpdateDTO", templateUpdateDTO);
	     mav.setViewName("updateTemplate");
	     
	    return mav;
	}
	
	
	@RequestMapping(value = "/process_update", method = RequestMethod.POST)
	public String saveUpdate(@ModelAttribute() TemplateUpdateDTO templateUpdateDTO,Authentication authentication) throws Exception {
		
		String username=authentication.getName();
		Template template= templateService.findById(templateUpdateDTO.getId());
		
		for (Authority auth: templateUpdateDTO.getAuthority()) {
			System.out.println(auth.name);
		}
		
		
		TemplateUpdateMapper templateUpdateMapper=new TemplateUpdateMapper();
		Template temp=templateUpdateMapper.toEntity(templateUpdateDTO,credentialService.findByUsername(username).getUserId(),template);
		
		for (Authority auth: temp.getAuths()) {
			System.out.println(auth.name);
		}
		
		
		
		templateService.updateTemplate(temp);
	    return "redirect:/template/manageTemplate";
	}
	
	
	
	
	
	@GetMapping("/manageTemplate")
	public ModelAndView showManagePage(Authentication authentication) {
		String name=authentication.getName();
		ModelAndView model = new ModelAndView("template");
		
		model.addObject("currentUser",credentialService.findByUsername(name).getUser());
		model.addObject("template",templateService.findAll());
		
		model.setViewName("manageTemplate");
		return model;
	}
	

	@GetMapping("/create")
	public ModelAndView showRegistrationForm() {
		ModelAndView model = new ModelAndView("templateDTO");
		model.setViewName("addTemplate");
		TemplateDTO templateDTO=new TemplateDTO();
		model.addObject("templateDTO",templateDTO);
		model.addObject("authorities",authorityService.findAll());
		return model;
	}
	
	@PostMapping("/process_create")
	public String processRegister(TemplateDTO templateDTO,Authentication authentication) {
		String username=authentication.getName();
		TemplateCreateMapper templateCreateMapper = new TemplateCreateMapper();
		Template template = templateCreateMapper.toEntity(templateDTO,credentialService.findByUsername(username).getUserId());
		templateService.updateTemplate(template);

		return "redirect:/template/manageTemplate";
}
	
	
}
