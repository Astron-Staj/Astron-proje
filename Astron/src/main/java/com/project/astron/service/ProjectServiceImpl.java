package com.project.astron.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.astron.model.Project;
import com.project.astron.repository.ProjectDataRepository;
@Service
public class ProjectServiceImpl implements IProjectService {

	
	@Autowired
	ProjectDataRepository projectDataRepository;
	
	@Override
	public List<Project> findAll() {
		return projectDataRepository.findAll();
	}

	@Override
	public void createProject(Project project) {
		projectDataRepository.save(project);
		
	}

	@Override
	public Optional<Project> findById(long id) {
		
		return projectDataRepository.findById(id);
	}

	@Override
	public void updateProject(Project project) {
		projectDataRepository.save(project);
		
	}

	@Override
	public void deleteProject(Project project) {
		projectDataRepository.delete(project);
		
	}

	
	
	
	
}
