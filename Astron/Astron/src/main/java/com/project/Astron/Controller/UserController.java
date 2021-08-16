package com.project.astron.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.astron.dto.UserCreateDTO;
import com.project.astron.dto.mapper.UserCreateMapper;
import com.project.astron.model.Credential;
import com.project.astron.model.User;
import com.project.astron.service.ICredentialService;
import com.project.astron.service.ITemplateService;
import com.project.astron.service.IUserService;
import com.project.astron.service.UserServiceImpl;



@Controller
@RequestMapping("user")
public class UserController {

	
	@Autowired
	IUserService userService;
	
	@Autowired 
	 ICredentialService credentialService;
	
	@Autowired
	ITemplateService templateService;
	
	@PreAuthorize("hasAuthority('USER_READ_ALL')")
	@GetMapping("all")
	public ModelAndView userTable() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users",userService.findAll());
		mav.setViewName("userRead");
		return mav;
	}
	
	@GetMapping("delete")
	public String delete() {
		try {
		
			credentialService.deleteCredential("ayse");
			return "register_success";
		} catch (Exception e) {
			
			return e.getMessage();
		}

	}
	
	
	
	
	
	@GetMapping("/register")
	public ModelAndView showRegistrationForm(UserCreateDTO userCreateDTO) {
		ModelAndView model = new ModelAndView("user");
		model.setViewName("register");
		model.addObject(userCreateDTO);
		return model;
	}
	
	
	@PostMapping("/process_register")
	public String processRegister(UserCreateDTO userCreateDTO) {
		UserCreateMapper mapper=new UserCreateMapper();
		
		List<Object> list=mapper.toEntity(userCreateDTO);
		try {
		
		
		User user= ((User)list.get(0)); 
		Credential credential=(Credential)list.get(1);
											//user= userService.createUser((User)list.get(0)); 
		user.getTemplates().add(templateService.findByName("USER"));
		credential.setUser(user);
		credentialService.createCredential(credential);
		
	    return "register_success";
	}
		catch (Exception e) {
			return e.getMessage();
		}
}
	
	
	
	
	
	

	
}
