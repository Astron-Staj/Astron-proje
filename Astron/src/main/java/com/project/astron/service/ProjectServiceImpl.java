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
	public void createProject(Project project) throws Exception {
		
		Optional<Project> p= projectDataRepository.findById(project.id);
		
		List<Project> list=this.findAll();
		boolean exist=false;
		for (Project prjct : list) {
			if(prjct.getProjectName().equals(project.getProjectName()))
				exist=true;
		}
		
		
		
		if(exist)
		throw new Exception("Aynı isimle proje mevcut");
		else
		projectDataRepository.save(project);
		
		
			
		
		
		
	}

	@Override
	public Optional<Project> findById(long id) throws Exception{
		Optional<Project> p= projectDataRepository.findById(id);
		if(p.get()==null)
			throw new Exception("Kayıt bulunamadı");
		else 
			return p;
		
		
	}

	@Override
	public void updateProject(Project project) throws Exception {
		Optional<Project> p= projectDataRepository.findById(project.id);
		List<Project> list=this.findAll();
		boolean exist=false;
		for (Project prjct : list) {
			if(prjct.getProjectName().equals(project.getProjectName())&&prjct.getId()!=project.getId())
				exist=true;
		}
		
			if(exist)
				throw new Exception("Aynı isimle proje mevcut");
			else
				projectDataRepository.save(project);
		
		
	}

	@Override
	public void deleteProject(Project project) {
		projectDataRepository.delete(project);
		
	}

	
	
	
	
	
	
	
}
