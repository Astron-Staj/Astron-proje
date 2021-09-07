package com.project.astron.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.astron.dto.AuthorityCreateDTO;
import com.project.astron.dto.AuthorityUpdateDTO;
import com.project.astron.dto.UserCreateDTO;
import com.project.astron.dto.mapper.AuthorityCreateMapper;
import com.project.astron.dto.mapper.UserCreateMapper;
import com.project.astron.model.Authority;
import com.project.astron.model.Credential;
import com.project.astron.model.User;
import com.project.astron.service.IAuthorityService;
import com.project.astron.service.ICredentialService;

@Controller
@RequestMapping("authority")
public class AuthorityController {

	@Autowired
	IAuthorityService authorityService;
	@Autowired 
	 ICredentialService credentialService;
	
	

	@PreAuthorize("hasAuthority('AUTHORITY_DELETE')")
	@Transactional
	@GetMapping("delete/{id}")
	public String delete(RedirectAttributes redirectAttributes, Authentication authentication,@PathVariable(value="id")long id,String error,String success,Model m) throws Exception {
		try {
			
			
			authorityService.deleteAuthority(id);
			redirectAttributes.addFlashAttribute("success", "Silme işlemi başarılı");
			return"redirect:/authority/manage/";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Silme işlemi sırasında hata oluştu:"+ e.getMessage());
			return"redirect:/authority/manage/";
		}

	}

	@PreAuthorize("hasAuthority('AUTHORITY_UPDATE')")
	@RequestMapping("/updateAuthority/{id}")
	public ModelAndView showEditPage(@PathVariable(value = "id") long id,String error,String success,Model m,Authentication authentication) throws Exception {
	   ModelAndView mav = new ModelAndView("auth");
	   try {  
		 
	    Authority auth = authorityService.findById(id);
	    AuthorityUpdateDTO dto=new AuthorityUpdateDTO(auth.getId(),auth.getName(),auth.isState());
	    mav.addObject("dto",dto);
	     mav.setViewName("update_authority");
	     
	     if (error != null)
	            m.addAttribute("error",error);
		  
		  if (success != null) 
			   m.addAttribute("success",success);
		  
	           
		    
	    return mav;
	    }
	  catch (Exception e) {
		  m.addAttribute("error", "Kayıt Bulunamadı");
		  return showManagePage(authentication,  "Kayıt Bulunamadı",null, m);
	}
	}
	
	@RequestMapping(value = "/saveUpdate", method = RequestMethod.POST)
	public String saveUpdate(RedirectAttributes redirectAttributes,@Valid @ModelAttribute("dto") AuthorityUpdateDTO dto,BindingResult bindingResult,String error,String success,Model m,Authentication authentication) throws Exception {
		 ModelAndView mav = new ModelAndView("dto");
		
		
		try {
		Authority authority= authorityService.findById(dto.id);
		String username=authentication.getName();
		authority.setUpdater(credentialService.findByUsername(username).getUserId());
		authority.setUpdated(new Date());
		authority.setName(dto.name);
		authority.setState(dto.state);
		mav.addObject("dto",dto);
		     mav.setViewName("update_authority");
	    if(bindingResult.hasErrors()) {
		    
		     return "update_authority";
		}
		
		
		
		authorityService.updateAuthority(authority);
		redirectAttributes.addFlashAttribute("success", "Güncelleme işlemi Başarılı");
		return"redirect:/authority/manage/";
		}catch (Exception e) {
			
			redirectAttributes.addFlashAttribute("error", "Güncelleme işlemi sırasında hata: "+e.getMessage());
			return"redirect:/authority/manage/";
		}
	    
	}
	@PreAuthorize("hasAuthority('AUTHORITY_MANAGE')")
	@GetMapping("/manage")
	public ModelAndView showManagePage(Authentication authentication,String error,String success,Model m) throws Exception{
		String username=authentication.getName();
		ModelAndView model = new ModelAndView();
		model.addObject("currentUser",credentialService.findByUsername(username).getUser());
		model.addObject("auths",authorityService.findActiveInactive());
		
		
		if (error != null)
            m.addAttribute("error",error);
	  
	  if (success != null)
            m.addAttribute("success",success);
	  model.setViewName("manageAuthority");
		return model;
	}
	
	
	
	@PreAuthorize("hasAuthority('AUTHORITY_READ_ALL')")
	@GetMapping("all")
	public ModelAndView authorityTable(Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("auths",authorityService.findAll());
		mav.setViewName("authorityRead");
		return mav;
	}
	
	@PreAuthorize("hasAuthority('AUTHORITY_CREATE')")
	  @RequestMapping(value="/newauthority", method=RequestMethod.GET)
	    public String authorityForm(String error,String success,Model model,AuthorityCreateDTO auth) {
	        model.addAttribute("auth", new AuthorityCreateDTO());
	        auth.setState(true);
	        model.addAttribute("auth",auth);
			 if (error != null)
		            model.addAttribute("error");
			  
			  if (success != null)
		            model.addAttribute("success");
	        return "newauthority";
	    }
	
	  @RequestMapping(value="/process_saved", method=RequestMethod.POST)
	    public String contactSubmit(RedirectAttributes redirectAttributes,@Valid @ModelAttribute("auth") AuthorityCreateDTO auth,BindingResult bindingResult, Model model,Authentication authentication) throws Exception {
		  if(bindingResult.hasErrors()) {
	 	        model.addAttribute("auth",auth);
	 	       return "newauthority";
	        }
		  
		  
		  
		  
		  try { 
			AuthorityCreateMapper mapper=new AuthorityCreateMapper();
		  String username=authentication.getName();
		  List<Object> list=mapper.toEntity(auth);
	        model.addAttribute("auth", auth);
	        
	        Authority aut = ((Authority)list.get(0)); 
	        aut.setCreator(credentialService.findByUsername(username).getUserId());
	        aut.setUpdater(credentialService.findByUsername(username).getUserId());
	        
	      	
				authorityService.createAuthority(aut);
				redirectAttributes.addFlashAttribute("success", "Kayıt başarılı");
				 return"redirect:/authority/newauthority/";
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("error", "Kayıt işlemi sırasında hata : "+ e.getMessage());
				 return"redirect:/authority/newauthority/";
			}
	        
	        
	    }
	  
	/*
	@GetMapping("newauthority")
	public ModelAndView createAuthority(AuthorityCreateDTO auth) {
		ModelAndView model = new ModelAndView("auth");
		model.addObject(auth);
		model.setViewName("newauthority");
		return model;
	}
	
	
	@PostMapping("/process_saved")
	public String processSaved(AuthorityCreateDTO authCreateDTO,Authentication authentication) {
		AuthorityCreateMapper mapper=new AuthorityCreateMapper();
		String username=authentication.getName();
		List<Object> list=mapper.toEntity(authCreateDTO);
		try {
		Authority auth= ((Authority)list.get(0)); 
		auth.setCreator(credentialService.findByUsername(username).getUserId());
		
	    return "saved_success";
	}
		catch (Exception e) {
			return e.getMessage();
		}
}
	*/
}
