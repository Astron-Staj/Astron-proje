package com.project.astron.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.astron.dto.SitemapCreateDTO;
import com.project.astron.dto.SitemapUpdateDTO;
import com.project.astron.dto.UserCreateDTO;
import com.project.astron.dto.mapper.UserCreateMapper;
import com.project.astron.model.Client;
import com.project.astron.model.Credential;
import com.project.astron.model.Project;
import com.project.astron.model.SiteMap;
import com.project.astron.model.Template;
import com.project.astron.model.User;
import com.project.astron.repository.SiteMapDataRepository;
import com.project.astron.service.IClientService;
import com.project.astron.service.ICredentialService;
import com.project.astron.service.ISiteMapService;

@Controller
@RequestMapping("sitemap")
public class SiteMapController {

	
	@Autowired
	ISiteMapService sitemapService;
	
	 @Autowired
	 IClientService clientService;
	
	 @Autowired
	 ICredentialService credentialService;
	
	
	@GetMapping("all/{id}")
	public ModelAndView userTable(@PathVariable(value="id")long id,Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("sitemaps",clientService.findById(id).get().getSitemaps());
		mav.setViewName("manageSiteMap");
		return mav;
	}
	
	
	@GetMapping("/create/{id}")
	public ModelAndView showRegistrationForm(@PathVariable(value="id")long id,SitemapCreateDTO sitemapCreateDTO) {
		ModelAndView model = new ModelAndView("user");
		model.setViewName("addSitemap");		
		sitemapCreateDTO.setClientId(id);
		model.addObject("sitemapcreateDTO",sitemapCreateDTO);
		return model;
	}
	
	
	@PostMapping("/process_create")
	public String processRegister(Authentication authentication,SitemapCreateDTO sitemapCreateDTO) {
		
		String username=authentication.getName();
		long userId=credentialService.findByUsername(username).getUserId();
		Date date=new Date();
		Optional<Client> client=clientService.findById(sitemapCreateDTO.getClientId());
		
		SiteMap siteMap =new SiteMap(sitemapCreateDTO.getUrl(), date,date,userId,userId,sitemapCreateDTO.getClientId(),client.get());
		long id=sitemapCreateDTO.getClientId();
		sitemapService.createSiteMap(siteMap);
	
		return "redirect:/sitemap/all/" +id ;
	
}
	
	
	@GetMapping("process_delete/{id}")
	public String delete(@PathVariable(value="id")long id) {
		
			Optional<SiteMap> siteMap=sitemapService.findById(id);
			siteMap.get().getProjects().clear();
			sitemapService.updateSiteMap(siteMap.get());
			siteMap=sitemapService.findById(id);
			sitemapService.deleteSiteMap(siteMap.get());
			return "redirect:/sitemap/all/"+ siteMap.get().getClientId();
	
	}
	
	
	@GetMapping("/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable(value="id")long id,SitemapUpdateDTO sitemapUpdateDTO) {
		ModelAndView model = new ModelAndView("user");
		model.setViewName("updateSitemap");		
		sitemapService.findById(id);
		sitemapUpdateDTO.setId(id);
		sitemapUpdateDTO.setUrl(sitemapService.findById(id).get().getUrl());
		model.addObject("sitemapcreateDTO",sitemapUpdateDTO);
		return model;
	}
	
	
	@PostMapping("/process_update")
	public String processUpdate(Authentication authentication,SitemapUpdateDTO sitemapUpdateDTO) {
		
		String username=authentication.getName();
		long id=credentialService.findByUsername(username).getUserId();
		Date date=new Date();
		
		Optional<SiteMap> sitemap=sitemapService.findById(sitemapUpdateDTO.getId());
		sitemap.get().setUrl(sitemapUpdateDTO.getUrl());
		sitemap.get().setUpdated(date);
		sitemap.get().setUpdater(id);
		
		sitemapService.updateSiteMap(sitemap.get());
		
	    return "redirect:/sitemap/all/"+sitemap.get().getClientId();
	
}
	
	
}
