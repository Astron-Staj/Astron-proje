package com.project.astron.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	 @PreAuthorize("hasAuthority('SITEMAP_READ_ALL')")
	@GetMapping("all/{id}")
	public ModelAndView userTable(String error,String success,Model m,@PathVariable(value="id")long id,Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("sitemaps",clientService.findById(id).get().getSitemaps());
		mav.setViewName("manageSiteMap");
		if (error != null)
            m.addAttribute("error",error);
	  
	  if (success != null)
            m.addAttribute("success",success);
		return mav;
	}
	
	 @PreAuthorize("hasAuthority('SITEMAP_CREATE')")
	@GetMapping("/create/{id}")
	public ModelAndView showRegistrationForm(Model m ,String success,String error,@PathVariable(value="id")long id,SitemapCreateDTO sitemapCreateDTO) {
		ModelAndView model = new ModelAndView("user");
		model.setViewName("addSitemap");		
		sitemapCreateDTO.setClientId(id);
		model.addObject("sitemapcreateDTO",sitemapCreateDTO);
		if (error != null)
            m.addAttribute("error");
	  
	  if (success != null)
            m.addAttribute("success");
		return model;
	}
	
	
	@PostMapping("/process_create")
	public String processRegister(RedirectAttributes redirectAttributes,@Valid @ModelAttribute("sitemapCreateDTO") SitemapCreateDTO sitemapCreateDTO,BindingResult bindingResult,Model model,Authentication authentication) throws Exception {
		
		String username=authentication.getName();
		long userId=credentialService.findByUsername(username).getUserId();
		Date date=new Date();
		 long id=sitemapCreateDTO.getClientId();
		SiteMap siteMap =new SiteMap();
		try {
			Optional<Client> client=clientService.findById(sitemapCreateDTO.getClientId());
			 siteMap =new SiteMap(sitemapCreateDTO.getUrl(), date,date,userId,userId,sitemapCreateDTO.getClientId(),client.get());
			
			
			ModelAndView mav = new ModelAndView("addSitemap");
			mav.addObject("sitemapcreateDTO",sitemapCreateDTO);
			
			if(bindingResult.hasErrors()) {
			return "addSitemap";
			}
				sitemapService.createSiteMap(siteMap);
				redirectAttributes.addFlashAttribute("success", "Kayıt başarılı");
				return "redirect:/sitemap/all/"+sitemapCreateDTO.getClientId();
		}catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Kayıt işlemi sırasında hata: "+e.getMessage());
			return "redirect:/sitemap/all/"+sitemapCreateDTO.getClientId();
		}
		
	
}
	
	 @PreAuthorize("hasAuthority('SITEMAP_DELETE')")
	@GetMapping("process_delete/{id}")
	public String delete(RedirectAttributes redirectAttributes,@PathVariable(value="id")long id,Model model,String error, String success,Authentication authentication) throws Exception {
		
			Optional<SiteMap> siteMap=sitemapService.findById(id);
			siteMap.get().getProjects().clear();
			
			siteMap=sitemapService.findById(id);
			try {
				sitemapService.updateSiteMap(siteMap.get());
				sitemapService.deleteSiteMap(siteMap.get());
				redirectAttributes.addFlashAttribute("success", "Silme işlemi başarılı");
				return "redirect:/sitemap/all/"+siteMap.get().getClientId();
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("error", "Silme işlemi sırasında hata: "+e.getMessage());
				return "redirect:/sitemap/all/"+siteMap.get().getClientId();
			}
			
			
	
	}
	
	 @PreAuthorize("hasAuthority('SITEMAP_UPDATE')")
	@GetMapping("/update/{id}")
	public ModelAndView showUpdateForm(Model m,String error, String success,@PathVariable(value="id")long id,SitemapUpdateDTO sitemapUpdateDTO,Authentication authentication) throws Exception {
		ModelAndView model = new ModelAndView("user");
		SiteMap sm=new SiteMap();
		model.setViewName("updateSitemap");		
		try {
			sm=sitemapService.findById(id).get();
		} catch (Exception e) {
		
			m.addAttribute("error", "Güncelleme işlemi sırasında hata: "+e.getMessage());
			return userTable( "Güncelleme işlemi sırasında hata: "+e.getMessage(),null,m,sm.clientId,authentication);
		}
		sitemapUpdateDTO.setId(id);
		sitemapUpdateDTO.setUrl(sm.getUrl());
		model.addObject("sitemapUpdateDTO",sitemapUpdateDTO);
		 if (error != null)
	            m.addAttribute("error",error);
		  
		  if (success != null)
	            m.addAttribute("success",success);
		return model;
	}
	
	
	@PostMapping("/process_update")
	public String processUpdate(RedirectAttributes redirectAttributes,@Valid @ModelAttribute("sitemapUpdateDTO") SitemapUpdateDTO sitemapUpdateDTO,BindingResult bindingResult,Model model,Authentication authentication) throws Exception {
		String username=authentication.getName();
		Date date=new Date();
		SiteMap sitemap=new SiteMap();
		
		try {
			sitemap=sitemapService.findById(sitemapUpdateDTO.getId()).get();
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Güncelleme işlemi sırasında hata: "+e.getMessage());
			return "redirect:/sitemap/update/"+sitemapUpdateDTO.getId();
		}
		
		try {
			ModelAndView mav = new ModelAndView("user");
			mav.setViewName("updateSitemap");		
			mav.addObject("sitemapUpdateDTO",sitemapUpdateDTO);
		sitemap=sitemapService.findById(sitemapUpdateDTO.getId()).get();
		long id=credentialService.findByUsername(username).getUserId();
		
		sitemap.setUrl(sitemapUpdateDTO.getUrl());
		sitemap.setUpdated(date);
		sitemap.setUpdater(id);
		
		if(bindingResult.hasErrors()) {
			return "updateSitemap";
		}
			
		
			sitemapService.updateSiteMap(sitemap);
			redirectAttributes.addFlashAttribute("success", "Güncelleme işlemi başarılı");
			return "redirect:/sitemap/update/"+sitemapUpdateDTO.getId();
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Güncelleme işlemi sırasında hata: "+e.getMessage());
			return "redirect:/sitemap/update/"+sitemapUpdateDTO.getId();
		}
		
	
}
	
	
}
