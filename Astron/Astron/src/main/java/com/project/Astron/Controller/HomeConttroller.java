package com.project.astron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.astron.service.ICredentialService;
import com.project.astron.service.IUserService;
import com.project.astron.service.UserServiceImpl;

@Controller
public class HomeConttroller {

	@Autowired
	IUserService userService;
	
	@Autowired
	ICredentialService credentialService;
	
	@GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
	
	
	@GetMapping({"/", "/dashboard"})
    public ModelAndView welcome(Model model,Authentication authentication) {
		String username=authentication.getName();
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.setViewName("index");
		return mav;
		
    }
	
	
	
	
	
	
	
	
	
	
}
