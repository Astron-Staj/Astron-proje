package com.project.astron.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
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

import com.project.astron.dto.ProjectCreateDTO;
import com.project.astron.dto.ProjectUpdateDTO;
import com.project.astron.dto.SitemapCreateDTO;
import com.project.astron.dto.SitemapUpdateDTO;
import com.project.astron.model.Client;
import com.project.astron.model.Project;
import com.project.astron.model.SiteMap;
import com.project.astron.service.ICredentialService;
import com.project.astron.service.IProjectService;
import com.project.astron.service.ISiteMapService;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

@Controller
@RequestMapping("project")
public class ProjectController {
	
	@Autowired
	ICredentialService credentialService;
	
	
	@Autowired
	IProjectService projectService;
	
	
	@Autowired
	ISiteMapService siteMapService;

	 @PreAuthorize("hasAuthority('PROJECT_CREATE')")
	@GetMapping("/create/{id}")
	public ModelAndView showRegistrationForm(Model m ,String success,String error,@PathVariable(value="id")long id,ProjectCreateDTO projectCreateDTO) {
		ModelAndView model = new ModelAndView();
		model.setViewName("addProject");		
		projectCreateDTO.setSiteMapId(id);
		model.addObject("projectCreateDTO",projectCreateDTO);
		if (error != null)
            m.addAttribute("error");
	  
	  if (success != null)
            m.addAttribute("success");
		return model;
	}
	
	
	@PostMapping("/process_create")
	public String processRegister( RedirectAttributes redirectAttributes,Model model,@Valid() @ModelAttribute("projectCreateDTO") ProjectCreateDTO projectCreateDTO,BindingResult bindingResult) throws Exception {
		
	
		Optional<SiteMap> siteMap=siteMapService.findById(projectCreateDTO.getSiteMapId());
		
		Project project=new Project(projectCreateDTO.getProjectName(),projectCreateDTO.getTaskCount(),projectCreateDTO.getSiteMapId(),siteMap.get());
		
		
		try {
		
			if(bindingResult.hasErrors()) {
				return "addProject";
			}
				
			projectService.createProject(project);
			redirectAttributes.addFlashAttribute("success", "Kayıt başarılı");
			return "redirect:/project/create/"+projectCreateDTO.getSiteMapId();
		} 
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("error","Kayıt işlemi sırasında hata:  "+e.getMessage());
			return "redirect:/project/create/"+projectCreateDTO.getSiteMapId();
			
		}
		
	
}
	 @PreAuthorize("hasAuthority('PROJECT_MANAGE')")
	@GetMapping("all/{id}")
	public ModelAndView userTable(Model model ,String success,String error,@PathVariable(value="id")long id,Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("projects",siteMapService.findById(id).get().getProjects());
		mav.setViewName("manageProject");
		if (error != null)
            model.addAttribute("error");
	  
	  if (success != null)
            model.addAttribute("success");
		return mav;
	}
	
	
	 @PreAuthorize("hasAuthority('PROJECT_MANAGE')")
	@GetMapping("/manage")
	public ModelAndView userTable(Model model ,String success,String error,Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		
		List<Project> userProjects=new ArrayList<Project>();
		for (Client clients : credentialService.findByUsername(username).getUser().getClients()) {
			for (SiteMap sitemap : clients.getSitemaps()) {
				for (Project project : sitemap.getProjects()) {
					if(clients.state)
					userProjects.add(project);
		}
				}
		}
		mav.addObject("projects",userProjects);
		mav.setViewName("manageProject");
		if (error != null)
            model.addAttribute("error");
	  
	  if (success != null)
            model.addAttribute("success");
		return mav;
	}
	
	
	 @PreAuthorize("hasAuthority('PROJECT_MANAGE_ALL')")
	@GetMapping("/manage/all")
	public ModelAndView manageAll(Model model ,String success,String error,Authentication authentication) throws Exception {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("projects",projectService.findAll());
		mav.setViewName("manageProject");
		if (error != null)
            model.addAttribute("error");
	  
	  if (success != null)
            model.addAttribute("success");
		return mav;
	}
	
	
	 @PreAuthorize("hasAuthority('PROJECT_UPDATE')")
	@GetMapping("/update/{id}")
	public ModelAndView showUpdateForm(Model m,String success,String error,@PathVariable(value="id")long id,ProjectUpdateDTO  projectUpdateDTO,Authentication authentication) throws Exception{
		ModelAndView model = new ModelAndView();
		model.setViewName("updateProject");		
		try {
		projectUpdateDTO.setId(id);
		projectUpdateDTO.setProjectName(projectService.findById(id).get().getProjectName());
		projectUpdateDTO.setTaskCount(projectService.findById(id).get().getTaskCount());
		}catch (Exception e) {
			m.addAttribute("error", "Güncelleme işlemi sırasında hata: "+ e.getMessage());
			return userTable(m, null, "Güncelleme işlemi sırasında hata: "+ e.getMessage(), authentication);
		}
		model.addObject("projectUpdateDTO",projectUpdateDTO);
		if (error != null)
            m.addAttribute("error");
	  
	  if (success != null)
            m.addAttribute("success");
		return model;
	}
	
	
	@PostMapping("/process_update")
	public String processUpdate(RedirectAttributes redirectAttributes,Model model,@Valid @ModelAttribute("projectUpdateDTO") ProjectUpdateDTO projectUpdateDTO,BindingResult bindingResult,Authentication authentication) throws Exception {
		
		try {
		
		Optional<Project> project=projectService.findById(projectUpdateDTO.getId());
		project.get().setProjectName(projectUpdateDTO.getProjectName());
		project.get().setTaskCount(projectUpdateDTO.getTaskCount());
		
		
			ModelAndView mav = new ModelAndView();
			mav.setViewName("updateProject");		
		
			mav.addObject("projectUpdateDTO",projectUpdateDTO);
			
			
			if(bindingResult.hasErrors()) {
				return "updateProject";
			}
			
			
			projectService.updateProject(project.get());
			redirectAttributes.addFlashAttribute("success", "Güncelleme başarılı");
			return "redirect:/project/manage/";
		}catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Güncelleme işlemi sırasında hata: "+ e.getMessage());
			return "redirect:/project/update/"+projectUpdateDTO.getId();
			
		}
		
		
	
	
}
	
	 @PreAuthorize("hasAuthority('PROJECT_DELETE')")
	@GetMapping("process_delete/{id}")
	public String delete(Model model ,String success,String error,@PathVariable(value="id")long id,Authentication authentication, RedirectAttributes redirectAttributes) throws Exception {
		
			Optional<Project> project=projectService.findById(id);
			
			
	
			try {
				projectService.deleteProject(project.get());
				redirectAttributes.addFlashAttribute("success", "Silme işlemi Başarılı");
				return "redirect:/project/manage";
			}catch (Exception e) {
				redirectAttributes.addFlashAttribute("error", "Silme işlemi sırasında hata: "+ e.getMessage());
				return "redirect:/project/manage";
			}
	}
	
	

}
