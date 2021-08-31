package com.project.astron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.astron.model.Project;

@Repository
public interface ProjectDataRepository extends JpaRepository<Project, Long> {

	
}
