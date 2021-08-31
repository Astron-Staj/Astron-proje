package com.project.astron.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.astron.dto.UserCreateDTO;
import com.project.astron.dto.UserSettingsDTO;
import com.project.astron.dto.UserUpdateDTO;
import com.project.astron.dto.mapper.UserCreateMapper;
import com.project.astron.dto.mapper.UserSettingsMapper;
import com.project.astron.dto.mapper.UserUpdateMapper;
import com.project.astron.model.Authority;
import com.project.astron.model.Client;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.service.IClientService;
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
	
	@Autowired
	IClientService clientService;
	
	
	@PreAuthorize("hasAuthority('USER_READ_ALL')")
	@GetMapping("all")
	public ModelAndView userTable(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("users",userService.findAll());
		mav.setViewName("userRead");
		return mav;
	}
	
	
	
	
	@GetMapping("process_delete/{id}")
	public String delete(@PathVariable(value="id")long id) {
		try {
			System.out.println(id);
			Credential cre=credentialService.findByUserId(id);
			cre.getUser().getTemplates().clear();
			cre.getUser().getClients().clear();
			credentialService.deleteCredential(cre.getUsername());
			return "redirect:/user/manage";
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
	public String processRegister(UserCreateDTO userCreateDTO,Authentication authentication) {
		UserCreateMapper mapper=new UserCreateMapper();
		String username=authentication.getName();
		List<Object> list=mapper.toEntity(userCreateDTO);
		try {
		User user= ((User)list.get(0)); 
		Credential credential=(Credential)list.get(1);
		user.setCreator(credentialService.findByUsername(username).getUserId());
		//user= userService.createUser((User)list.get(0)); 
		List <Template> temps=new ArrayList<Template>();
		temps=user.getTemplates();
		temps.add(templateService.findByName("USER"));
		user.setTemplates(temps);
		credential.setUser(user);
		credentialService.createCredential(credential);
		
	    return "redirect:/user/manage";
	}
		catch (Exception e) {
			return e.getMessage();
		}
}
	
	
	
	@GetMapping("/manage")
	public ModelAndView showManagePage(Authentication authentication) {
		
		String username=authentication.getName();
		ModelAndView model = new ModelAndView("user");
		model.addObject("currentUser",credentialService.findByUsername(username).getUser());
		model.addObject("users",userService.findAll());
		model.setViewName("manageUser");
		return model;
	}
	
	

	
	@RequestMapping("/update/{id}")
	public ModelAndView showEditPage(@PathVariable(value = "id") long id,Authentication authentication) {
		String username=authentication.getName();
	    ModelAndView mav = new ModelAndView("userUpdateDTO");
	   UserUpdateDTO userUpdateDTO= new UserUpdateDTO();
	   Credential credential=credentialService.findByUserId(id);
	   System.out.println(credential.getUsername());
	   userUpdateDTO.setUsername(credential.getUsername());
	   userUpdateDTO.setEmail(credential.getUser().getEmail());
	   userUpdateDTO.setFirstName(credential.getUser().getFirstName());
	   userUpdateDTO.setLastName(credential.getUser().getLastName());
	   userUpdateDTO.setState(credential.getUser().getState());
	   Template[] templateList =new Template[ credential.getUser().getTemplates().size()];
   		for(int i=0;i<credential.getUser().getTemplates().size();i++) {
   			templateList[i]=credential.getUser().getTemplates().get(i);
   		}
	   	
   	 Client[] clientList =new Client[ credential.getUser().getClients().size()];
		for(int i=0;i<credential.getUser().getClients().size();i++) {
			clientList[i]=credential.getUser().getClients().get(i);
		}
	   	
		//userUpdateDTO.setClients(clientList);
	   userUpdateDTO.setTemplates(templateList);
	   mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
	   mav.addObject("template",templateService.findAll());
	   mav.addObject("client",clientService.findAll());
	    mav.addObject("userUpdateDTO", userUpdateDTO);
	     mav.setViewName("updateUser");
	     
	    return mav;
	}
	
	@RequestMapping(value = "/process_update", method = RequestMethod.POST)
	public String saveUpdate(@ModelAttribute() UserUpdateDTO userUpdateDTO,Authentication authentication) throws Exception {
		
		String username=authentication.getName();
		Credential credential= credentialService.findByUsername(userUpdateDTO.getUsername());
		
		
		
		UserUpdateMapper userUpdateMapper=new UserUpdateMapper();
		List<Object> list=userUpdateMapper.toEntity(userUpdateDTO,credential.getUser(),credential,credentialService.findByUsername(username).getUserId());
		Credential cre=(Credential)list.get(1);
		cre.setUser((User)list.get(0));
		
		credentialService.updateCredential(cre);
	    return "redirect:/user/manage";
	}
/*
	@RequestMapping(value="/user/updateUser/{id}",method=RequestMethod.GET)
	public String updateUserFind(@PathVariable long id, ModelMap modelMap) {
		User user = userService.getUserByID(id);
		modelMap.put("user", user); 
		return "update_user";
	}
	
	



	*/
	
	
	@GetMapping("/profile")
	public ModelAndView userProfile(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		Credential credential=credentialService.findByUsername(username);
		mav.addObject("currentUser",credential.getUser());
		
		
		mav.addObject("credential",credential);
		mav.addObject("templates",credential.getUser().getTemplates());
		
		mav.setViewName("userProfile");
		return mav;
	}
	
	
	@GetMapping("/settings")
	public ModelAndView userSettings(Authentication authentication) {
		String username=authentication.getName();
	    ModelAndView mav = new ModelAndView("userSettingsDTO");
	    
	  UserSettingsDTO userSettingsDTO=new UserSettingsDTO();
	   Credential credential=credentialService.findByUsername(username);
	   System.out.println(credential.getUsername());
	   userSettingsDTO.setUsername(credential.getUsername());
	   userSettingsDTO.setEmail(credential.getUser().getEmail());
	   userSettingsDTO.setFirstName(credential.getUser().getFirstName());
	   userSettingsDTO.setLastName(credential.getUser().getLastName());
	   userSettingsDTO.setState(credential.getUser().getState());
	
	   mav.addObject("currentUser",credential.getUser());
	   mav.addObject("template",templateService.findAll());
	   
	    mav.addObject("userSettingsDTO", userSettingsDTO);
	     mav.setViewName("userSettings");
	     return mav;
	}
	
	@RequestMapping(value = "/process_settings", method = RequestMethod.POST)
	public String profileUpdate(@ModelAttribute() UserSettingsDTO userSettingsDTO,Authentication authentication) throws Exception {
		
		String username=authentication.getName();
		Credential credential= credentialService.findByUsername(username);
		
		
		UserSettingsMapper userUpdateMapper=new UserSettingsMapper();
		List<Object> list=userUpdateMapper.toEntity(userSettingsDTO,credential.getUser(),credential,credentialService.findByUsername(username).getUserId());
		Credential cre=(Credential)list.get(1);
		cre.setUser((User)list.get(0));
		
		credentialService.updateCredential(cre);
	    return "redirect:/dashboard";
	}
	
	
}
