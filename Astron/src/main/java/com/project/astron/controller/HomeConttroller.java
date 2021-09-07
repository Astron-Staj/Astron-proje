package com.project.astron.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.astron.model.Client;
import com.project.astron.model.Credential;
import com.project.astron.model.Project;
import com.project.astron.model.SiteMap;
import com.project.astron.model.User;
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
            model.addAttribute("error", "Kullanıcı adı veya şifre hatalı!");

        if (logout != null)
            model.addAttribute("message", "Başarıyla çıkış yaptınız.");

        return "login";
    }
	
	
	@GetMapping("/forbidden")
    public String login() {
        

        return "accessDenied";
    }
	
	
	@GetMapping({"/", "/dashboard","","welcome"})
    public ModelAndView welcome(Model model,Authentication authentication) throws Exception {
		String username=authentication.getName();
		ModelAndView mav = new ModelAndView();
		Date date = new Date();
		Credential credential=credentialService.findByUsername(username);
		User user=credential.getUser();
		user.setLastLogin(date);
		credential.setUser(user);
		credentialService.updateCre(credential);
		userService.updateUser(user);
		
		long projectCount=0;
		for (Client client : credentialService.findByUsername(username).getUser().getClients()) {
			for (SiteMap siteMap : client.getSitemaps()) {
				projectCount+=siteMap.getProjects().size();
				
			}
			
		}
		long taskCount=0;
		
		for (Client client : credentialService.findByUsername(username).getUser().getClients()) {
			for (SiteMap siteMap : client.getSitemaps()) {
				for (Project project : siteMap.getProjects()) {
					taskCount+=project.taskCount;
					
				}
				
			}
			
		}
		
		
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("clients",credentialService.findByUsername(username).getUser().getClients());
		mav.addObject("projectCount",projectCount);
		mav.addObject("imgUtil", new ImageUtil());
		mav.addObject("clientCount",credentialService.findByUsername(username).getUser().getClients().size());
		mav.addObject("totalTasks",taskCount);
		mav.setViewName("index");
		return mav;
		
    }
	
	
	
	
	
	
	
	
}
