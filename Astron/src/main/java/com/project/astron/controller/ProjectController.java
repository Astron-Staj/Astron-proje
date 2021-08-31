package com.project.astron.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

@Controller
@RequestMapping("project")
public class ProjectController {
	
	@Autowired
	ICredentialService credentialService;
	
	
	@Autowired
	IProjectService projectService;
	
	
	@Autowired
	ISiteMapService siteMapService;

	@GetMapping("/create/{id}")
	public ModelAndView showRegistrationForm(@PathVariable(value="id")long id,ProjectCreateDTO projectCreateDTO) {
		ModelAndView model = new ModelAndView();
		model.setViewName("addProject");		
		projectCreateDTO.setSiteMapId(id);
		model.addObject("projectCreateDTO",projectCreateDTO);
		return model;
	}
	
	
	@PostMapping("/process_create")
	public String processRegister(ProjectCreateDTO projectCreateDTO) {
		
	
		Optional<SiteMap> siteMap=siteMapService.findById(projectCreateDTO.getSiteMapId());
		
		Project project=new Project(projectCreateDTO.getProjectName(),projectCreateDTO.getTaskCount(),projectCreateDTO.getSiteMapId(),siteMap.get());
		
		projectService.createProject(project);
		
	    return "redirect:/project/all/"+projectCreateDTO.getSiteMapId();
	
}
	
	@GetMapping("all/{id}")
	public ModelAndView userTable(@PathVariable(value="id")long id,Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("projects",siteMapService.findById(id).get().getProjects());
		mav.setViewName("manageProject");
		return mav;
	}
	
	

	@GetMapping("/manage")
	public ModelAndView userTable(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		
		List<Project> userProjects=new ArrayList<Project>();
		for (Client clients : credentialService.findByUsername(username).getUser().getClients()) {
			for (SiteMap sitemap : clients.getSitemaps()) {
				for (Project project : sitemap.getProjects()) {
					userProjects.add(project);
		}
				}
		}
		mav.addObject("projects",userProjects);
		mav.setViewName("manageProject");
		return mav;
	}
	
	
	
	@GetMapping("/manage/all")
	public ModelAndView manageAll(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		String username=authentication.getName();
		mav.addObject("currentUser",credentialService.findByUsername(username).getUser());
		mav.addObject("projects",projectService.findAll());
		mav.setViewName("manageProject");
		return mav;
	}
	
	
	@GetMapping("/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable(value="id")long id,ProjectUpdateDTO  projectUpdateDTO) {
		ModelAndView model = new ModelAndView();
		model.setViewName("updateProject");		
		
		projectUpdateDTO.setId(id);
		projectUpdateDTO.setProjectName(projectService.findById(id).get().getProjectName());
		projectUpdateDTO.setTaskCount(projectService.findById(id).get().getTaskCount());
			
		model.addObject("projectUpdateDTO",projectUpdateDTO);
		return model;
	}
	
	
	@PostMapping("/process_update")
	public String processUpdate(ProjectUpdateDTO projectUpdateDTO) {
		

		
		Optional<Project> project=projectService.findById(projectUpdateDTO.getId());
		project.get().setProjectName(projectUpdateDTO.getProjectName());
		project.get().setTaskCount(projectUpdateDTO.getTaskCount());
		
		projectService.updateProject(project.get());
		
	    return "redirect:/project/manage/all";
	
}
	
	
	@GetMapping("process_delete/{id}")
	public String delete(@PathVariable(value="id")long id) {
		
			Optional<Project> project=projectService.findById(id);
			projectService.deleteProject(project.get());
			return "redirect:/project/manage/all";
	
	}
	
	

}
