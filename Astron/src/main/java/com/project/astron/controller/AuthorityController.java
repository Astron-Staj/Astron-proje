package com.project.astron.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
	
	//@PreAuthorize("hasAuthority('AUTHORITY_READ_ALL')")

	@GetMapping("authority/dessslete/{id}")
	public String deleteUser(@PathVariable(value="id") Long id,
			Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		
			authorityService.deleteAuthority(id);
			redirectAttributes.addFlashAttribute("message","The Authority ID" + id + "has been deleted");
		
		System.out.println();
		return "redirect:/authority/manage";
	}

	@Transactional
	@GetMapping("delete/{id}")
	public String delete(@PathVariable(value="id")long id) {
		try {
			
			Optional<Authority> auth=authorityService.findAuthById(id);
		//System.out.println("xxxxxx"+auth.get().getName());
			
			
			 auth.get().removeTemplates();
			authorityService.deleteAuthority(auth.get());
			return "redirect:/authority/manage";
		} catch (Exception e) {
			
			return e.getMessage();
		}

	}

	@RequestMapping("/updateAuthority/{id}")
	public ModelAndView showEditPage(@PathVariable(value = "id") long id) {
	    ModelAndView mav = new ModelAndView("auth");
	    Authority auth = authorityService.findById(id);
	    mav.addObject("auth", auth);
	     mav.setViewName("update_authority");
	     
	    return mav;
	}
	
	@RequestMapping(value = "/saveUpdate", method = RequestMethod.POST)
	public String saveUpdate(@ModelAttribute() Authority auth,Authentication authentication) throws Exception {
		
		String username=authentication.getName();
		auth.setUpdater(credentialService.findByUsername(username).getUserId());
		auth.setUpdated(new Date());
		//Date created = auth.getCreated();
		//auth.setState(true);
		//auth.setCreated(created);
		authorityService.saveAuthority(auth);
	     
	    return "redirect:/authority/manage";
	}
	
	@GetMapping("/manage")
	public ModelAndView showManagePage(Authentication authentication) {
		String username=authentication.getName();
		ModelAndView model = new ModelAndView("auth");
		model.addObject("currentUser",credentialService.findByUsername(username).getUser());
		model.addObject("auths",authorityService.findAll());
		model.setViewName("manageAuthority");
		return model;
	}
	
	
	
	
	@GetMapping("all")
	public ModelAndView authorityTable(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("auths",authorityService.findAll());
		mav.setViewName("authorityRead");
		return mav;
	}
	
	  @RequestMapping(value="/newauthority", method=RequestMethod.GET)
	    public String authorityForm(Model model) {
	        model.addAttribute("auth", new AuthorityCreateDTO());
	        return "newauthority";
	    }
	
	  @RequestMapping(value="/process_saved", method=RequestMethod.POST)
	    public String contactSubmit(@ModelAttribute AuthorityCreateDTO auth, Model model,Authentication authentication) throws Exception {
		  AuthorityCreateMapper mapper=new AuthorityCreateMapper();
		  String username=authentication.getName();
		  List<Object> list=mapper.toEntity(auth);
	        model.addAttribute("auth", auth);
	        
	        Authority aut = ((Authority)list.get(0)); 
	        aut.setCreator(credentialService.findByUsername(username).getUserId());
	        aut.setUpdater(credentialService.findByUsername(username).getUserId());
	        authorityService.createAuthority(aut);
	        return "saved_success";
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
