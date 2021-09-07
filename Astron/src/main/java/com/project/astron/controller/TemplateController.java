package com.project.astron.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	
	@PreAuthorize("hasAuthority('TEMPLATE_READ_ALL')")
	@GetMapping("/all")
	public ModelAndView getAll(Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("templates",templateService.findAll());
		mav.setViewName("templateRead");
		return mav;
	}
	
	@PreAuthorize("hasAuthority('TEMPLATE_DELETE')")
	@Transactional
	@GetMapping("process_delete/{id}")
	public String delete(RedirectAttributes redirectAttributes,@PathVariable(value="id")long id,Model model,String error, String success,Authentication authentication) throws Exception {
		
		
		try {
			System.out.println(id);
			

			Optional<Template> template=templateService.findTemplate(id);
		
			
			templateService.deleteTemplateById(template.get().getId());
			redirectAttributes.addFlashAttribute("success", "Silme işlemi başarılı");
			 return"redirect:/template/manageTemplate/";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Silme Işleminde Hata: "+e.getMessage());
			 return"redirect:/template/manageTemplate/";
		}

	}
	


	@PreAuthorize("hasAuthority('TEMPLATE_UPDATE')")
	@RequestMapping("/update/{id}")
	public ModelAndView showEditPage(@PathVariable(value = "id") long id,Authentication authentication,Model model,String error, String success) throws Exception {
		   ModelAndView mav = new ModelAndView("templateUpdateDTO");
		try {
			Template template = templateService.findById(id);
			String username=authentication.getName();		  
		    TemplateUpdateDTO templateUpdateDTO=new TemplateUpdateDTO();
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
		    
		    if (error != null)
	            model.addAttribute("error",error);
		  
		  if (success != null)
	         model.addAttribute("success",success);
		  
		     mav.setViewName("updateTemplate");
		     return mav;
		} catch (Exception e) {
			  model.addAttribute("error","Kayıt bulunamadı");
			  return showManagePage(authentication, "Kayıt bulunamadı", null, model);
		}
		
	
	}
	
	
	@RequestMapping(value = "/process_update", method = RequestMethod.POST)
	public ModelAndView saveUpdate(Model model,@Valid @ModelAttribute("templateUpdateDTO") TemplateUpdateDTO templateUpdateDTO,BindingResult bindingResult,Authentication authentication) throws Exception {
			try {
		
		
	
		String username=authentication.getName();
		Template template= templateService.findById(templateUpdateDTO.getId());
	
		TemplateUpdateMapper templateUpdateMapper=new TemplateUpdateMapper();
		Template temp=templateUpdateMapper.toEntity(templateUpdateDTO,credentialService.findByUsername(username).getUserId(),template);
			if(bindingResult.hasErrors())
			{
				
				 ModelAndView mav = new ModelAndView("updateTemplate");
				mav.addObject("currentUser",credentialService.findByUsername(authentication.getName()).getUser());
				   mav.addObject("authorities",authorityService.findAll());
				   mav.addObject("template",template);
				    mav.addObject("templateUpdateDTO", templateUpdateDTO);
				    return mav;
			}
		
			templateService.updateTemplate(temp);
			model.addAttribute("success", "Güncelleme işlemi Başarılı");
			return showManagePage(authentication, null, "Güncelleme işlemi Başarılı", model);
		}catch (Exception e) {
			model.addAttribute("error", "Güncelleme işlemi sırasında hata : "+e.getMessage());
			return showManagePage(authentication,"Güncelleme işlemi sırasında hata : "+e.getMessage(),null, model);
		}
		
		
	    
	}
	
	
	
	
	@PreAuthorize("hasAuthority('TEMPLATE_MANAGE')")
	@GetMapping("/manageTemplate")
	public ModelAndView showManagePage(Authentication authentication,String error,String success,Model m) throws Exception {
		String name=authentication.getName();
		ModelAndView model = new ModelAndView("template");
		
		model.addObject("currentUser",credentialService.findByUsername(name).getUser());
		model.addObject("template",templateService.findActiveInactive());
		if (error != null)
            m.addAttribute("error",error);
	  
	  if (success != null)
            m.addAttribute("success",success);
		model.setViewName("manageTemplate");
		return model;
	}
	
	@PreAuthorize("hasAuthority('TEMPLATE_CREATE')")
	@GetMapping("/create")
	public ModelAndView showRegistrationForm(Model model ,String success,String error,TemplateDTO templateDTO) {
		ModelAndView mav = new ModelAndView("templateDTO");
		mav.setViewName("addTemplate");
		templateDTO.setState(true);
		mav.addObject("templateDTO",templateDTO);
		mav.addObject("authorities",authorityService.findAll());
		
		
		 if (error != null)
	            model.addAttribute("error");
		  
		  if (success != null)
	            model.addAttribute("success");
		return mav;
	}
	
	@PostMapping("/process_create")
	public ModelAndView processRegister(@Valid @ModelAttribute("templateDTO")TemplateDTO templateDTO,BindingResult bindingResult,Authentication authentication,Model model) throws Exception {
	try {	
		String username=authentication.getName();
		TemplateCreateMapper templateCreateMapper = new TemplateCreateMapper();
		Template template = templateCreateMapper.toEntity(templateDTO,credentialService.findByUsername(username).getUserId());
		if(bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("addTemplate");
			mav.setViewName("addTemplate");
			templateDTO.setState(true);
			mav.addObject("templateDTO",templateDTO);
			mav.addObject("authorities",authorityService.findAll());
			return mav;
		}
		
		
		templateService.saveTemplate(template);
		model.addAttribute("success", "Kayıt başarılı");
		return showRegistrationForm(model, "Kayıt başarılı", null,new TemplateDTO());
		}catch (Exception e) {
			model.addAttribute("error", "Kayıt işlemi sırasında hata : "+e.getMessage());
			return showRegistrationForm(model,  null,"Kayıt işlemi sırasında hata : "+e.getMessage(),new TemplateDTO());
		}
		
}
	
	
}
