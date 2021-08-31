package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import com.project.astron.model.Client;
import com.project.astron.model.Project;
import com.project.astron.model.SiteMap;

public interface IProjectService {

	void createProject(Project project);
	List<Project> findAll();
	Optional<Project> findById(long id);
	void updateProject(Project project);
	void deleteProject(Project project);
	
	
}
