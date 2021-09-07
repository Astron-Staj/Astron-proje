package com.project.astron.controller;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.aspectj.weaver.NewConstructorTypeMunger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@PreAuthorize("hasAuthority('CLIENT_MANAGE_ALL')")
	@GetMapping("/all")
	public ModelAndView userTable(String error,String success,Model m,Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("clients",clientService.findActiveInactive());
		mav.addObject("imgUtil", new ImageUtil());
		
		
		if (error != null)
            m.addAttribute("error",error);
	  
	  if (success != null)
            m.addAttribute("success",success);
		
		mav.setViewName("manageAllClients");
		return mav;
	}
	
	
	@PreAuthorize("hasAuthority('CLIENT_MANAGE_YOURS')")
	@GetMapping("/manage")
    public ModelAndView getClients(Authentication authentication,String error,String success,Model m) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		Credential credential = credentialService.findByUsername(authentication.getName());
		mav.addObject("clients",credential.getUser().getClients());	
		mav.addObject("imgUtil", new ImageUtil());
		
		
		if (error != null)
            m.addAttribute("error",error);
	  
	  if (success != null)
            m.addAttribute("success",success);
		
		mav.setViewName("manageClient");
        return mav ;
    }
	
	@PreAuthorize("hasAuthority('CLIENT_CREATE')")
	@GetMapping("/create")
	public ModelAndView showRegistrationForm(Model m,String error, String success,ClientCreateDTO clientCreateDTO) {
		ModelAndView model = new ModelAndView("clientCreateDTO");
 		model.setViewName("createClient");
 		clientCreateDTO.setState(true);
		model.addObject(clientCreateDTO);
	
		if (error != null)
            m.addAttribute("error");
	  
	  if (success != null)
            m.addAttribute("success");
		
		
		return model;
	}
	

	@PostMapping("/process_create")
	public String processRegister(RedirectAttributes redirectAttributes,@Valid @ModelAttribute("clientCreateDTO") ClientCreateDTO dto,BindingResult bindingResult ,Model model,Authentication authentication) {
		
		ClientCreateMapper clientCreateMapper=new ClientCreateMapper();
		String username=authentication.getName();
			ModelAndView mav = new ModelAndView("createClient");
			mav.addObject("clientCreateDTO",dto);
		
		if (bindingResult.hasErrors()) {
		
			return "createClient";
		}
		
		try {
		User user= credentialService.findByUsername(username).getUser();
		Client client=clientCreateMapper.toEntity(dto, user,dto.getLogo());
		clientService.createClient(client);
		user.getClients().add(client);
	
		userService.updateUser(user);
		redirectAttributes.addFlashAttribute("success", "Kayıt başarılı");
		return "redirect:/client/create/";
	}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Kayıt işlemi sırasında hata oluştu:"+e.getMessage());
			return "redirect:/client/create/";
		}
}
	
	@PreAuthorize("hasAuthority('CLIENT_DELETE')")
	@GetMapping("process_delete/{id}")
	public String delete(RedirectAttributes redirectAttributes,Model model,String error, String success,@PathVariable(value="id")long id,Authentication authentication) throws Exception {
		try {
		Optional<Client> client=clientService.findById(id);
			
			 client=clientService.findById(id);
			clientService.deleteClient(client.get());
			redirectAttributes.addFlashAttribute("success", "Silme işlemi başarılı");
			return "redirect:/client/manage/";
		} catch (Exception e) {
			
			redirectAttributes.addFlashAttribute("error", "Silme işlemi sırasında hata oluştu: "+e.getMessage());
			return "redirect:/client/manage/";
		}

	}
	@PreAuthorize("hasAuthority('CLIENT_UPDATE')")
	@GetMapping("/update/{id}")
	public ModelAndView showEditPage(Model model,String error, String success,@PathVariable(value = "id") long id,Authentication authentication) throws Exception  {
		 ModelAndView mav = new ModelAndView("clientUpdateDTO");
	  ClientUpdateDTO dto = new ClientUpdateDTO();
	   Optional<Client> clientOpt=clientService.findById(id);
	   Client client=new Client();
	try {
		client = clientService.findByCode(clientOpt.get().code);
	} catch (Exception e) {
		model.addAttribute("error","Güncelleme işlemi sırasında hata"+e.getMessage() );
		return getClients(authentication, "Güncelleme işlemi sırasında hata"+e.getMessage(), null, model);
		
	}
		
		String username=authentication.getName();
	
	
	  dto.setCode(client.getCode());
	   dto.setName(client.getName());
	   dto.setState(client.getState());
	 
	   mav.addObject("currentUser",credentialService.findByUsername(username).getUser());

	    mav.addObject("clientUpdateDTO",dto);
	    if (error != null)
            model.addAttribute("error",error);
	  
	  if (success != null)
            model.addAttribute("success",success);
	     mav.setViewName("updateTemplate");
	    
	    
	     mav.setViewName("updateClient");
	   
	    return mav;
	}
	
	@RequestMapping(value = "/process_update", method = RequestMethod.POST)
	public ModelAndView saveUpdate(@Valid @ModelAttribute("clientUpdateDTO") ClientUpdateDTO clientUpdateDTO,BindingResult bindingResult,Model model,String error, String success,Authentication authentication) throws Exception  {
		

		long id;
		try {
			id = clientService.findByCode(clientUpdateDTO.getCode()).getId();
		} catch (Exception e1) {
			model.addAttribute("error","Güncelleme işlemi sırasında hata"+e1.getMessage() );
			return getClients(authentication,"Güncelleme işlemi sırasında hata"+e1.getMessage() , null, model);
		}	
		ModelAndView mav = new ModelAndView("updateClient");
			mav.addObject("currentUser",credentialService.findByUsername(authentication.getName()).getUser());
			 mav.addObject("clientUpdateDTO",clientUpdateDTO);
		if(bindingResult.hasErrors()) {
		
			 return mav;
		}
		try {
			Credential credential= credentialService.findByUsername(authentication.getName());

		Client client=clientService.findByCode(clientUpdateDTO.getCode());
		client.setName(clientUpdateDTO.getName());
		client.setState(clientUpdateDTO.isState());
		client.setUpdated(new Date());
		client.setUpdater(credential.getUserId());
		
			
		
			
			
			clientService.updateClient(client);
			model.addAttribute("success", "Güncelleme işlemi Başarılı");
			return getClients(authentication, null, "Güncelleme işlemi Başarılı", model);
			
		} catch (Exception e) {
			model.addAttribute("error", "Güncelleme işlemi sırasında hata oluştu"+e.getMessage());
			return showEditPage(model,"Güncelleme işlemi sırasında hata oluştu"+e.getMessage(), null, id , authentication);
		}
		
	}
	
	
	
	
	
	
	
	
}
