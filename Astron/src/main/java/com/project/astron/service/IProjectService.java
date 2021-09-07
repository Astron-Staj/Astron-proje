package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Client;
import com.project.astron.model.Project;
import com.project.astron.model.SiteMap;

public interface IProjectService {

	void createProject(Project project)throws Exception ;
	List<Project> findAll();
	
	Optional<Project> findById(long id) throws Exception;
	void updateProject(Project project)throws Exception ;
	void deleteProject(Project project);
	
	
}
