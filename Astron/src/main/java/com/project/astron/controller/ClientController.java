package com.project.astron.controller;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.NewConstructorTypeMunger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.project.astron.dto.ClientCreateDTO;
import com.project.astron.dto.ClientUpdateDTO;
import com.project.astron.dto.UserCreateDTO;
import com.project.astron.dto.UserUpdateDTO;
import com.project.astron.dto.mapper.ClientCreateMapper;
import com.project.astron.dto.mapper.UserCreateMapper;
import com.project.astron.dto.mapper.UserUpdateMapper;
import com.project.astron.model.Client;
import com.project.astron.model.Credential;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.service.IClientService;
import com.project.astron.service.ICredentialService;
import com.project.astron.service.IUserService;

@Controller
@RequestMapping("client")
public class ClientController {

	@Autowired
	IClientService clientService;
	
	
	@Autowired
	ICredentialService credentialService;
	
	@Autowired
	IUserService userService;
	
	
	@GetMapping("/all")
	public ModelAndView userTable(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("clients",clientService.findAll());
		mav.addObject("imgUtil", new ImageUtil());
		mav.setViewName("manageAllClients");
		return mav;
	}
	
	
	
	@GetMapping("/manage")
    public ModelAndView getClients(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		Credential credential = credentialService.findByUsername(authentication.getName());
		mav.addObject("clients",credential.getUser().getClients());	
		mav.addObject("imgUtil", new ImageUtil());
		mav.setViewName("manageClient");
        return mav ;
    }
	

	@GetMapping("/create")
	public ModelAndView showRegistrationForm(ClientCreateDTO clientCreateDTO) {
		ModelAndView model = new ModelAndView("clientCreateDTO");
 		model.setViewName("createClient");
		model.addObject(clientCreateDTO);
		return model;
	}
	

	@PostMapping("/process_create")
	public String processRegister(Authentication authentication,ClientCreateDTO dto) {
		
		ClientCreateMapper clientCreateMapper=new ClientCreateMapper();
		String username=authentication.getName();
		
		try {
		User user= credentialService.findByUsername(username).getUser();
		Client client=clientCreateMapper.toEntity(dto, user,dto.getLogo());
		clientService.createClient(client);
		user.getClients().add(client);
		userService.updateUser(user);
	    return "redirect:/client/all";
	}
		catch (Exception e) {
			return e.getMessage();
		}
}
	
	
	@GetMapping("process_delete/{id}")
	public String delete(@PathVariable(value="id")long id,Authentication authentication) {
		try {
		Optional<Client> client=clientService.findById(id);
			Credential cre=credentialService.findByUsername(authentication.getName());
			User user=cre.getUser();
			user.getClients().remove(client.get());
			userService.updateUser(user);
			client.get().getUsers().clear();
			client.get().getSitemaps().clear();
			clientService.updateClient(client.get());
			 client=clientService.findById(id);
			clientService.deleteClient(client.get());
			return "redirect:/client/manage";
		} catch (Exception e) {
			
			return e.getMessage();
		}

	}
	
	@GetMapping("/update/{id}")
	public ModelAndView showEditPage(@PathVariable(value = "id") long id,Authentication authentication) {
		String username=authentication.getName();
	    ModelAndView mav = new ModelAndView("clientUpdateDTO");
	  ClientUpdateDTO dto = new ClientUpdateDTO();
	   Optional<Client> clientOpt=clientService.findById(id);
	   Client client=clientService.findByCode(clientOpt.get().code);
	  dto.setCode(client.getCode());
	   dto.setName(client.getName());
	   dto.setState(client.getState());
	 
	   mav.addObject("currentUser",credentialService.findByUsername(username).getUser());

	    mav.addObject("clientUpdateDTO",dto);
	     mav.setViewName("updateClient");
	   
	    return mav;
	}
	
	@RequestMapping(value = "/process_update", method = RequestMethod.POST)
	public String saveUpdate(@ModelAttribute() ClientUpdateDTO clientUpdateDTO,Authentication authentication) throws Exception {
		

		Credential credential= credentialService.findByUsername(authentication.getName());
		

		Client client=clientService.findByCode(clientUpdateDTO.getCode());
		client.setName(clientUpdateDTO.getName());
		client.setState(clientUpdateDTO.isState());
		client.setUpdated(new Date());
		client.setUpdater(credential.getUserId());
		clientService.updateClient(client);
	    return "redirect:/client/manage";
	}
	
	
	
	
	
	
	
	
}
