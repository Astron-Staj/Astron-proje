package com.project.astron.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
	public ModelAndView userTable(Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("users",userService.findAll());
		mav.setViewName("userRead");
		return mav;
	}
	
	
	
	@PreAuthorize("hasAuthority('USER_DELETE')")
	@GetMapping("process_delete/{id}")
	public ModelAndView delete(Authentication authentication,@PathVariable(value="id")long id,Model model,String error, String success) throws Exception {
		String username=authentication.getName();
		ModelAndView mav = new ModelAndView("user");
		
		
		try {
			System.out.println(id);
			Credential cre=credentialService.findByUserId(id);
			credentialService.deleteCredential(cre.getUsername(),username);
			mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
			mav.addObject("users",userService.findActiveInactive());
			mav.setViewName("manageUser");
			model.addAttribute("success", "Silme işlemi başarılı");
			return mav;
		} catch (Exception e) {
			mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
			mav.addObject("users",userService.findActiveInactive());
			mav.setViewName("manageUser");
			model.addAttribute("error", "Silme Işleminde Hata: "+e.getMessage());
			return mav;
		}

	}
	
	
	
	
	@PreAuthorize("hasAuthority('USER_REGISTER')")
	@GetMapping("/register")
	public ModelAndView showRegistrationForm(Model model ,String success,String error,UserCreateDTO userCreateDTO) {
		ModelAndView mav = new ModelAndView("user");
		mav.setViewName("register");
		mav.addObject(userCreateDTO);
		
		  if (error != null)
	            model.addAttribute("error");
		  
		  if (success != null)
	            model.addAttribute("success");
		
	       

		return mav;
	}
	
	
	@PostMapping("/process_register")
	public String processRegister(@Valid @ModelAttribute("userCreateDTO")UserCreateDTO userCreateDTO,BindingResult bindingResult,Authentication authentication,Model model ) {
		UserCreateMapper mapper=new UserCreateMapper();
		String username=authentication.getName();
		List<Object> list=mapper.toEntity(userCreateDTO);
		if (bindingResult.hasErrors()) {
				return "register";
			}
		
		try {
			
		User user= ((User)list.get(0)); 
		Credential credential=(Credential)list.get(1);
		user.setCreator(credentialService.findByUsername(username).getUserId());
		//user= userService.createUser((User)list.get(0)); 
		List <Template> temps=new ArrayList<Template>();
		temps=user.getTemplates();
		temps.add(templateService.findByName("USER"));
		user.setTemplates(temps);
		user.setState(true);
		credential.setUser(user);
		credentialService.createCredential(credential);
		model.addAttribute("success", "Kayıt başarılı");
	    return "register";
	}
		catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
}
	
	
	@PreAuthorize("hasAuthority('USER_MANAGE')")
	@GetMapping("/manage")
	public ModelAndView showManagePage(Authentication authentication,String error,String success,Model m) throws Exception {
		
		String username=authentication.getName();
		ModelAndView model = new ModelAndView("user");
		model.addObject("currentUser",credentialService.findByUsername(username).getUser());
		model.addObject("users",userService.findActiveInactive());
		model.setViewName("manageUser");
		if (error != null)
            m.addAttribute("error",error);
	  
	  if (success != null)
            m.addAttribute("success",success);
		return model;
	}
	
	

	@PreAuthorize("hasAuthority('USER_UPDATE')")
	@RequestMapping("/update/{id}")
	public ModelAndView showEditPage(Model model,@PathVariable(value = "id") long id,Authentication authentication,String success,String error) throws Exception {
		String username=authentication.getName();
	    ModelAndView mav = new ModelAndView("userUpdateDTO");
	   UserUpdateDTO userUpdateDTO= new UserUpdateDTO();
	   Credential credential=credentialService.findByUserId(id);
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
	   	
		userUpdateDTO.setClients(clientList);
	   userUpdateDTO.setTemplates(templateList);
	   mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
	   mav.addObject("template",templateService.findAll());
	   mav.addObject("client",clientService.findAll());
	    mav.addObject("userUpdateDTO", userUpdateDTO);
	     mav.setViewName("updateUser");
	     
	     if (error != null)
	            model.addAttribute("error");
		  
		  if (success != null)
	            model.addAttribute("success");
	     
	    return mav;
	}
	
	@RequestMapping(value = "/process_update", method = RequestMethod.POST)
	public ModelAndView saveUpdate(@Valid @ModelAttribute("userUpdateDTO")UserUpdateDTO userUpdateDTO,BindingResult bindingResult,Authentication authentication,Model model) throws Exception {
		Credential credential=new Credential();
		String username=authentication.getName();
		try {
		credential= credentialService.findByUsername(userUpdateDTO.getUsername());
		}catch (Exception e) {
			model.addAttribute("error", "Güncelleme işlemi sırasında hata"+e.getMessage());
			return showManagePage(authentication, "Güncelleme işlemi sırasında hata"+e.getMessage(),null, model);
		}
		ModelAndView mav =new ModelAndView("updateUser");
		mav.addObject("userUpdateDTO"	,userUpdateDTO);
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("template",templateService.findAll());
		mav.addObject("client",clientService.findAll());
		UserUpdateMapper userUpdateMapper=new UserUpdateMapper();
		List<Object> list=userUpdateMapper.toEntity(userUpdateDTO,credential.getUser(),credential,credentialService.findByUsername(username).getUserId());
		Credential cre=(Credential)list.get(1);
		cre.setUser((User)list.get(0));
		
		if (bindingResult.hasErrors()) {
	
			return mav;
		}
		
		try {
			credentialService.updateCredential(cre,credentialService.findByUsername(username).getUserId());
			model.addAttribute("success", "Güncelleme işlemi Başarılı");
			return showManagePage(authentication, null, "Güncelleme işlemi Başarılı", model);
			
		} catch (Exception e) {
			model.addAttribute("error", "Hata:"+e.getMessage());
			return showEditPage(model, cre.getUserId(), authentication,null,"Hata:"+e.getMessage());
		}
		
		
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
	public ModelAndView userProfile(Authentication authentication,Model model , String error,String success) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		Credential credential=credentialService.findByUsername(username);
		mav.addObject("currentUser",credential.getUser());
		
		
		mav.addObject("credential",credential);
		mav.addObject("templates",credential.getUser().getTemplates());
		
		mav.setViewName("userProfile");
		
		
		if (error != null)
            model.addAttribute("error");
	  
	  if (success != null)
            model.addAttribute("success");
		return mav;
	}
	
	
	@GetMapping("/settings")
	public ModelAndView userSettings(Authentication authentication,Model model,String success,String error) throws Exception {
		String username=authentication.getName();
	    ModelAndView mav = new ModelAndView("userSettingsDTO");
	    
	  UserSettingsDTO userSettingsDTO=new UserSettingsDTO();
	   Credential credential=credentialService.findByUsername(username);
	  
	   userSettingsDTO.setUsername(credential.getUsername());
	   userSettingsDTO.setEmail(credential.getUser().getEmail());
	   userSettingsDTO.setFirstName(credential.getUser().getFirstName());
	   userSettingsDTO.setLastName(credential.getUser().getLastName());
	   
	
	   mav.addObject("currentUser",credential.getUser());
	   mav.addObject("template",templateService.findAll());
	   
	    mav.addObject("userSettingsDTO", userSettingsDTO);
	     mav.setViewName("userSettings");
	     
	     
	     if (error != null)
	            model.addAttribute("error");
		  
		  if (success != null)
	            model.addAttribute("success");
	     return mav;
	}
	
	@RequestMapping(value = "/process_settings", method = RequestMethod.POST)
	public ModelAndView profileUpdate(@Valid @ModelAttribute("userSettingsDTO") UserSettingsDTO userSettingsDTO,BindingResult bindingResult,Model model,Authentication authentication) throws Exception {
		
		String username=authentication.getName();
		Credential credential= credentialService.findByUsername(username);
		
		
		UserSettingsMapper userUpdateMapper=new UserSettingsMapper();
		List<Object> list=userUpdateMapper.toEntity(userSettingsDTO,credential.getUser(),credential,credentialService.findByUsername(username).getUserId());
		Credential cre=(Credential)list.get(1);
		cre.setUser((User)list.get(0));
			ModelAndView mav =new ModelAndView("userSettings");
		   mav.addObject("currentUser",credential.getUser());
		   mav.addObject("template",templateService.findAll());
		    mav.addObject("userSettingsDTO", userSettingsDTO);
		  
		
		if (bindingResult.hasErrors()) {	
			return mav;
		}
		
		
		try {
		credentialService.updateCre(cre);
		model.addAttribute("success", "Profiliniz güncellendi");
		return userProfile(authentication,model,null,"Profiliniz güncellendi");	
		} catch (Exception e) {

			model.addAttribute("error", "Hata:"+e.getMessage());
			return userSettings(authentication, model, null, "Hata:"+e.getMessage());
			
		}
	}
	
	
}
